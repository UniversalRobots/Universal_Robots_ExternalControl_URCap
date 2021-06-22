cmake_minimum_required(VERSION 3.0.2)
project(urcap_test)

find_package(ur_client_library REQUIRED)

add_executable(test_external_control
  test_external_control_urcap.cpp)
target_link_libraries(test_external_control ur_client_library::urcl gtest)
