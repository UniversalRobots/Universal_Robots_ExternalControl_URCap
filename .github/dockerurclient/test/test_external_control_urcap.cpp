#include <gtest/gtest.h>
#include <ur_client_library/log.h>
#include <ur_client_library/types.h>
#include <ur_client_library/ur/dashboard_client.h>
#include <ur_client_library/ur/ur_driver.h>

#include <chrono>
#include <condition_variable>
#include <iostream>
#include <memory>
#include <thread>

using namespace urcl;

const std::string ROBOT_IP = "192.168.56.101";
const std::string SCRIPT_FILE = "/usr/local/share/ur_client_library/resources/external_control.urscript";
const std::string OUTPUT_RECIPE = "resources/rtde_output_recipe.txt";
const std::string INPUT_RECIPE = "resources/rtde_input_recipe.txt";

std::condition_variable g_handle_program_state_cv;
std::mutex g_handle_program_state_mutex;
bool g_program_state;
bool g_program_calback;

void handleRobotProgramState(bool program_running)
{
  std::lock_guard<std::mutex> lk(g_handle_program_state_mutex);
  g_program_state = program_running;
  g_handle_program_state_cv.notify_one();
  g_program_calback = true;
}

bool waitForProgramState(int milliseconds = 200)
{
  std::unique_lock<std::mutex> lk(g_handle_program_state_mutex);
  if (g_handle_program_state_cv.wait_for(lk, std::chrono::milliseconds(milliseconds)) == std::cv_status::no_timeout ||
      g_program_calback == true)
  {
    g_program_calback = false;
    return true;
  }
  else
  {
    return false;
  }
}

/*!
 * \brief This is testing that a program with urcap can load and run. It also verifies that commands can be sent to
 * the robot, while the program is running. This test requires the universal robots client library in order to run.
 */
TEST(UrClient, external_control_test)
{
  setLogLevel(LogLevel::INFO);

  std::unique_ptr<UrDriver> driver;
  std::unique_ptr<DashboardClient> dashboard_client;
  std::unique_ptr<ToolCommSetup> tool_comm_setup;

  g_program_calback = false;

  vector6d_t joint_speeds;
  for (unsigned int i = 0; i < 6; ++i)
  {
    joint_speeds[i] = 0.0;
  }

  dashboard_client.reset(new DashboardClient(ROBOT_IP));
  driver.reset(new UrDriver(ROBOT_IP, SCRIPT_FILE, OUTPUT_RECIPE, INPUT_RECIPE, &handleRobotProgramState, false,
                            std::move(tool_comm_setup), 50001, 50002, 2000, 0.03, false));

  ASSERT_TRUE(waitForProgramState());
  ASSERT_FALSE(g_program_state);

  timeval tv;
  tv.tv_sec = 5;
  tv.tv_usec = 0.0;
  dashboard_client->setReceiveTimeout(tv);
  dashboard_client->connect();

  // Get the robot into to running
  dashboard_client->sendAndReceive("brake release\n");
  std::string robot_mode = dashboard_client->sendAndReceive("robotmode\n");
  std::chrono::steady_clock::time_point end = std::chrono::steady_clock::now() + std::chrono::seconds(10);
  while (robot_mode != "Robotmode: RUNNING" && std::chrono::steady_clock::now() < end)
  {
    robot_mode = dashboard_client->sendAndReceive("robotmode\n");
    std::cout << robot_mode << std::endl;
    std::this_thread::sleep_for(std::chrono::milliseconds(250));
  }
  ASSERT_EQ(robot_mode, "Robotmode: RUNNING");

  dashboard_client->sendAndReceive("play\n");
  ASSERT_TRUE(waitForProgramState());
  ASSERT_TRUE(g_program_state);

  unsigned int count = 0;
  while (count < 500)  // keep the connection for one second
  {
    ASSERT_TRUE(driver->writeJointCommand(joint_speeds, comm::ControlMode::MODE_SPEEDJ));
    std::this_thread::sleep_for(std::chrono::milliseconds(2));  // e-series cycle time
    count++;
  }

  dashboard_client->sendAndReceive("stop\n");
  ASSERT_TRUE(waitForProgramState());
  ASSERT_FALSE(g_program_state);
}

int main(int argc, char* argv[])
{
  ::testing::InitGoogleTest(&argc, argv);

  return RUN_ALL_TESTS();
}
