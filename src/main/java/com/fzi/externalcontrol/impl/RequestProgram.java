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

import java.io.DataOutputStream;
import java.io.IOException;
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

  public void sendCommand(BuildCommand scriptCommand) {
    String command = commandToString(scriptCommand);

    try {
      // socket creation
      Socket socket = new Socket(hostIp, portNr);
      if (socket.isConnected()) {
        // output stream creation
        DataOutputStream out;
        out = new DataOutputStream(socket.getOutputStream());

        // send command
        out.write(command.getBytes("US-ASCII"));
        out.flush();
        out.close();
      }
      socket.close();
    } catch (IOException e) {
      System.err.println(e);
    }
  }

  public String commandToString(BuildCommand scriptCommand) {
    return scriptCommand.toString();
  }
}
