// -- BEGIN LICENSE BLOCK ----------------------------------------------
// Copyright 2019 FZI Forschungszentrum Informatik
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// -- END LICENSE BLOCK ------------------------------------------------

//----------------------------------------------------------------------
/*!\file
 *
 * \author  Lea Steffen steffen@fzi.de
 * \date    2019-05-22
 *
 */
//----------------------------------------------------------------------
package com.fzi.externalcontrol.impl;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;

public class RequestProgram {
  // custom IP
  private final String hostIp;
  // custom port
  private final int portNr;

  /*
   * Default constructor
   */
  public RequestProgram(String hostIp, String portNr) {
    this.hostIp = hostIp;
    this.portNr = Integer.parseInt(portNr);
  }

  public String sendCommand(String command) {
    // String command = scriptCommand.toString();
    String result = "";
    try {
      // socket creation
      Socket socket = new Socket();

      // 5 second timeout (make configurable?)
      int timeout = 5*1000;
      socket.connect(new InetSocketAddress(this.hostIp, this.portNr), timeout);
      System.out.println("hostIp is: " + this.hostIp);
      System.out.println("portNr is: " + this.portNr);


      if (socket.isConnected()) {
        // output stream creation
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        // send command
        out.write(command.getBytes("US-ASCII"));

        DataInputStream in = new DataInputStream(socket.getInputStream());
        BufferedReader buff = new BufferedReader(new InputStreamReader(in));

        // wait for buffer...
        result += (" " + buff.readLine());

        while (buff.ready()) {
          result += "\n";
          result += (" " + buff.readLine());
        }

        out.flush();
        out.close();
      }
      socket.close();
    } catch (IOException e) {
      result = String.format("popup(\"The connection to the remote PC at " + this.hostIp + ":" + this.portNr + " could not be established. Reason: %s\","
          + "\"Receive program failed\", False, True, blocking=True)\n"
          + "sync()", e.getMessage());
    }
    return result;
  }
}
