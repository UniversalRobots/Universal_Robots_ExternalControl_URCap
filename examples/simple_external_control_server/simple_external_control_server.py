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

# A simple python 3 server to test the External Control URCap
# The server answer request from the URCap with the context of the given file,
# it is used inside simple_external_control_server

import socketserver
import threading
import argparse


class ThreadedTCPServer(socketserver.ThreadingMixIn, socketserver.TCPServer):
    daemon_threads = True
    allow_reuse_address = True

    def __init__(self, server_address, RequestHandlerClass, file):
        self.file = file
        super().__init__(server_address, RequestHandlerClass)


class FileHandler(socketserver.StreamRequestHandler):
    def __init__(self, request, client_address, server):
        self.file = server.file
        super().__init__(request, client_address, server)

    def handle(self):
        client = f'{self.client_address} on {threading.currentThread().getName()}'
        print(f'Connected: {client}')
        file = open(self.file, "r")
        while True:
            data = file.read()

            print(data)
            if not data:
                break
            self.wfile.write(data.encode('utf-8'))
        file.close()
        print(f'Closed: {client}')


if __name__ == '__main__':
    parser = argparse.ArgumentParser(description='Simple External Control server')
    parser.add_argument(
        "file", type=str, help="Path to the UR script file, which will be sent to the robot")
    parser.add_argument("-p", "--port", type=int,
                        default=50002, help="Port number to use")

    args = parser.parse_args()

    server = ThreadedTCPServer(('', args.port), FileHandler, args.file)
    print(f'The Simple External Control server is running on port {args.port}')
    try:
        server.serve_forever()
    except KeyboardInterrupt:
        pass

    server.server_close()
