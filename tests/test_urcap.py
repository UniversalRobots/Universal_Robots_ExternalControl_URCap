#!/usr/bin/env python3

# // -- BEGIN LICENSE BLOCK ----------------------------------------------
# // Copyright 2021 Universal Robots A/S
# //
# // Licensed under the Apache License, Version 2.0 (the "License");
# // you may not use this file except in compliance with the License.
# // You may obtain a copy of the License at
# //
# //     http://www.apache.org/licenses/LICENSE-2.0
# //
# // Unless required by applicable law or agreed to in writing, software
# // distributed under the License is distributed on an "AS IS" BASIS,
# // WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# // See the License for the specific language governing permissions and
# // limitations under the License.
# // -- END LICENSE BLOCK ------------------------------------------------

import sys
import os
import unittest
import socket
import sys
import time
import threading

from examples.simple_external_control_server.simple_external_control_server import ThreadedTCPServer, FileHandler

# Make sure we can find the external control server
sys.path.append(os.path.abspath(os.path.join(os.path.dirname(__file__), '..')))
from examples.simple_external_control_server.simple_external_control_server import ThreadedTCPServer, FileHandler


ROBOT_IP = "127.0.0.1"


class DashboardClient:
    def __init__(self, ip_address):
        self.ip_address = ip_address
        self.port = 29999

    def connect(self):
        self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.socket.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)

        self.socket.connect((self.ip_address, self.port))
        self.buffer_size = 1024
        self.socket.settimeout(5)
        self.socket.recv(self.buffer_size)

    def disconnect(self):
        self.socket.close()

    def sendAndRecieve(self, command):
        self.socket.settimeout(10)
        self.socket.sendall((command + "\n").encode("utf-8"))
        msg = self.socket.recv(self.buffer_size)
        msg = msg.decode("utf-8")
        msg = msg.replace('\n', '')
        return msg


class ServerThread(threading.Thread):
    def __init__(self, server):
        threading.Thread.__init__(self)
        self.server = server

    def run(self):
        self.server.serve_forever()


class TestUrcap(unittest.TestCase):
    client = None
    server = None
    server_thread = None

    @classmethod
    def setUpClass(cls):
        cls.init_robot(cls)

    @classmethod
    def tearDownClass(cls):
        cls.client.disconnect()
        print("tearing down")
        cls.server.shutdown()
        print("done shutting down server")
        cls.server_thread.join()

    def init_robot(self):
        """Setup server to send urscript to urcap and connect to dashboard client."""
        self.server = ThreadedTCPServer(('', 50002), FileHandler, os.path.dirname(
            __file__) + "/resources/test_script.urscript")
        self.server_thread = ServerThread(self.server)
        self.server_thread.start()

        self.client = DashboardClient(ROBOT_IP)
        self.client.connect()

    def run_program_test(self, program):
        # Load program
        self.client.sendAndRecieve(f"load {program}.urp")
        t_end = time.time() + 5
        while time.time() < t_end:
            result = self.client.sendAndRecieve("get loaded program")
            if program in result:
                break
        self.assertTrue(program in result)

        self.client.sendAndRecieve("brake release")

        # Make sure the robot is running
        t_end = time.time() + 10
        while time.time() < t_end:
            robot_mode = self.client.sendAndRecieve("robotmode")
            if robot_mode == "Robotmode: RUNNING":
                break
        self.assertEqual(robot_mode, "Robotmode: RUNNING")

        # Play program
        self.client.sendAndRecieve("play")
        t_end = time.time() + 10
        while time.time() < t_end:
            program_state = self.client.sendAndRecieve("programState")
            if "PLAYING" in program_state:
                break
        self.assertTrue("PLAYING" in program_state)

        # Wait for program to stop again
        t_end = time.time() + 10
        while time.time() < t_end:
            program_state = self.client.sendAndRecieve("programState")
            if "STOPPED" in program_state:
                break
        self.assertTrue("STOPPED" in program_state)

    def test_one_urcap(self):
        """This is testing that a program with the urcap can load and run."""
        self.run_program_test("one_urcap")

    def test_multiple_urcaps(self):
        """Test that a program with multiple URCaps can load and run, this will test # HEADER_BEGIN # HEADER_END feature."""
        self.run_program_test("multiple_urcaps")


if __name__ == '__main__':
    unittest.main()
