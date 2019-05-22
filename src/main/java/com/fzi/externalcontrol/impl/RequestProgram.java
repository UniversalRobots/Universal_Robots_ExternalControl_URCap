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
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class RequestProgram {
  private final String sendIp;
  private final int sendPort;

  private final String returnIp;
  private final int returnPort;

  /*
   * Default constructor
   */
  public RequestProgram(String sendIp, String sendPort) {
    this.sendIp = sendIp;
    this.sendPort = Integer.parseInt(sendPort);
    this.returnIp = this.sendIp;
    this.returnPort = 5500; // Integer.parseInt(returnPort);
  }

  public String readValueFromRobot(BuildCommand command) {
    String input = "";

    try {
      // socket creation
      ServerSocket server = new ServerSocket(returnPort);
      ScriptSender sender = new ScriptSender(sendIp, sendPort);
      sender.sendCommand(command);

      Socket returnSocket = server.accept();

      BufferedReader readFromURScript =
          new BufferedReader(new InputStreamReader(returnSocket.getInputStream()));
      input = readFromURScript.readLine();

      // cleaunp
      readFromURScript.close();
      returnSocket.close();
      server.close();

    } catch (IOException e) {
      System.err.println(e);
    }
    System.out.println("readValueFromRobot: " + input);
    return input;
  }
}
