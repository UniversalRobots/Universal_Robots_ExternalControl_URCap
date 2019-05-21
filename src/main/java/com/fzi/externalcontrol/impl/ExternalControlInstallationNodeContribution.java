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
 * \date    2019-04-18
 *
 */
//----------------------------------------------------------------------

package com.fzi.externalcontrol.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.ur.urcap.api.contribution.InstallationNodeContribution;
import com.ur.urcap.api.contribution.installation.InstallationAPIProvider;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;
import com.ur.urcap.api.domain.userinteraction.keyboard.KeyboardInputCallback;
import com.ur.urcap.api.domain.userinteraction.keyboard.KeyboardInputFactory;
import com.ur.urcap.api.domain.userinteraction.keyboard.KeyboardTextInput;

public class ExternalControlInstallationNodeContribution implements InstallationNodeContribution {
  // socket
  private static final String HOST_IP = "192.168.0.104";
  private static final int PORT_NR =
      30001; // 30001= primary, 30002= scondary, 30003= real-time, 30004= RTDE
  private Socket rosSocket = null;
  private String urScriptProgram = null;

  // bare bone
  private static final String DEFAULT_VALUE = "192.168.1.254";
  private DataModel model;
  private final ExternalControlInstallationNodeView view;
  private final KeyboardInputFactory keyboardFactory;

  public ExternalControlInstallationNodeContribution(InstallationAPIProvider apiProvider,
      ExternalControlInstallationNodeView view, DataModel model) {
    this.keyboardFactory =
        apiProvider.getUserInterfaceAPI().getUserInteraction().getKeyboardInputFactory();
    this.model = model;
    this.view = view;
  }

  @Override
  public void openView() {}

  @Override
  public void closeView() {}

  public boolean isDefined() {
    return !getHostIP().isEmpty();
  }

  @Override
  public void generateScript(ScriptWriter writer) {
    // TODO Auto-generated method stub
  }

  public void setHostIP(String ip) {
    if ("".equals(ip)) {
      resetToDefaultValue();
    } else {
      model.set(HOST_IP, ip);
    }
  }

  public String getHostIP() {
    return model.get(HOST_IP, DEFAULT_VALUE);
  }

  private void resetToDefaultValue() {
    model.set(HOST_IP, DEFAULT_VALUE);
  }

  public KeyboardTextInput getInputForTextField() {
    KeyboardTextInput keyboInput = keyboardFactory.createStringKeyboardInput();
    keyboInput.setInitialValue(getHostIP());
    return keyboInput;
  }

  public KeyboardInputCallback<String> getCallbackForTextField() {
    return new KeyboardInputCallback<String>() {
      @Override
      public void onOk(String value) {
        setHostIP(value);
        view.UpdateIPTextField(value);
      }
    };
  }

  public void requestProgram() {
    // boolean socketOpen = false;

    for (int i = 0; i < 20; i++) {
      try {
        rosSocket = new Socket(HOST_IP, PORT_NR);

        OutputStream out = rosSocket.getOutputStream();
        PrintStream ps = new PrintStream(out, true);
        ps.println("Request");

        InputStream in = rosSocket.getInputStream();
        System.out.println("Received bytes" + in.available());
        BufferedReader buff = new BufferedReader(new InputStreamReader(in));

        while (buff.ready()) {
          System.out.println(buff.readLine());
          urScriptProgram = buff.readLine();
        }
      } catch (UnknownHostException e) {
        System.err.println("Socket can not be opened due to Unkown host");
        e.printStackTrace();
      } catch (IOException e) {
        System.err.println("Socket can not be opened due to IO issues");
        e.printStackTrace();
      } finally {
        if (rosSocket != null)
          try {
            rosSocket.close();
            System.out.println("Socket is closed.");
          } catch (IOException e) {
            System.err.println("Closing socket was not possible.");
            e.printStackTrace();
          }
      }
    }
  }

  public String getUrScriptProgram() {
    return urScriptProgram;
  }
}