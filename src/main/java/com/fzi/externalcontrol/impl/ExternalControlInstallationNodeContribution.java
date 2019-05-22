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

import com.ur.urcap.api.contribution.InstallationNodeContribution;
import com.ur.urcap.api.contribution.installation.InstallationAPIProvider;
import com.ur.urcap.api.domain.data.DataModel;
import com.ur.urcap.api.domain.script.ScriptWriter;
import com.ur.urcap.api.domain.userinteraction.keyboard.KeyboardInputCallback;
import com.ur.urcap.api.domain.userinteraction.keyboard.KeyboardInputFactory;
import com.ur.urcap.api.domain.userinteraction.keyboard.KeyboardTextInput;

import com.jbm.urcap.sample.scriptCommunicator.communicator.ScriptCommand;
import com.jbm.urcap.sample.scriptCommunicator.communicator.ScriptSender;


public class ExternalControlInstallationNodeContribution implements InstallationNodeContribution {
  // socket
  private static final String HOST_IP = "";
  private static final String PORT_NR = "";
  private String urScriptProgram = null;
  private final ScriptSender sender;

  private final RequestProgram programRequest;

  // bare bone
  private static final String DEFAULT_IP = "127.0.0.1";
  private static final String DEFAULT_PORT = "30002";
  private DataModel model;
  private final ExternalControlInstallationNodeView view;
  private final KeyboardInputFactory keyboardFactory;

  public ExternalControlInstallationNodeContribution(InstallationAPIProvider apiProvider,
      ExternalControlInstallationNodeView view, DataModel model) {
    this.keyboardFactory =
        apiProvider.getUserInterfaceAPI().getUserInteraction().getKeyboardInputFactory();
    this.model = model;
    this.view = view;
    this.sender = new ScriptSender();
    this.programRequest = new RequestProgram(getHostIP(), getHostPort());
  }

  public void makePopupTest() {
    ScriptCommand sendtestCommand = new ScriptCommand("requestProgram");
    sendtestCommand.appendLine("popup(\"This is a popup\")");
    System.out.println("make popup: " + sendtestCommand);
    System.out.println();
    sender.sendScriptCommand(sendtestCommand);
  }

  public void requestProgram() {
    BuildCommand sendtestCommand = new BuildCommand("requestProgram");
    sendtestCommand.appendLine("popup(\"This is a popup\")");
    System.out.println("requestProgram: " + sendtestCommand);
    System.out.println();
    programRequest.sendCommand(sendtestCommand);
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

  // IP helper functions
  public void setHostIP(String ip) {
    if ("".equals(ip)) {
      resetToDefaultIP();
    } else {
      model.set(HOST_IP, ip);
    }
  }

  public String getHostIP() {
    return model.get(HOST_IP, DEFAULT_IP);
  }

  private void resetToDefaultIP() {
    model.set(HOST_IP, DEFAULT_IP);
  }

  public KeyboardTextInput getInputForIPTextField() {
    KeyboardTextInput keyboInput = keyboardFactory.createStringKeyboardInput();
    keyboInput.setInitialValue(getHostIP());
    return keyboInput;
  }

  public KeyboardInputCallback<String> getCallbackForIPTextField() {
    return new KeyboardInputCallback<String>() {
      @Override
      public void onOk(String value) {
        setHostIP(value);
        view.UpdateIPTextField(value);
      }
    };
  }

  // port helper functions
  public void setHostport(String port) {
    if ("".equals(port)) {
      resetToDefaultPort();
    } else {
      model.set(PORT_NR, port);
    }
  }

  public String getHostPort() {
    return model.get(PORT_NR, DEFAULT_PORT);
  }

  private void resetToDefaultPort() {
    model.set(PORT_NR, DEFAULT_PORT);
  }

  public KeyboardTextInput getInputForPortTextField() {
    KeyboardTextInput keyboInput = keyboardFactory.createStringKeyboardInput();
    keyboInput.setInitialValue(getHostPort());
    return keyboInput;
  }

  public KeyboardInputCallback<String> getCallbackForPortTextField() {
    return new KeyboardInputCallback<String>() {
      @Override
      public void onOk(String value) {
        setHostIP(value);
        view.UpdatePortTextField(value);
      }
    };
  }

  public String getUrScriptProgram() {
    return urScriptProgram;
  }
}