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

public class ExternalControlInstallationNodeContribution implements InstallationNodeContribution {
  private static final String HOST_IP = "host_ip";
  private static final String PORT_NR = "port_nr";
  private static final String NAME = "name";
  private static final String DEFAULT_IP = "192.168.56.1";
  private static final String DEFAULT_PORT = "50002";
  private static final String DEFAULT_NAME = DEFAULT_IP;
  private DataModel model;
  private final ExternalControlInstallationNodeView view;
  private final KeyboardInputFactory keyboardFactory;
  private RequestProgram sender;
  private boolean programRequested;

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
    programRequested = false;
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
  public void setHostPort(String port) {
    if ("".equals(port)) {
      resetToDefaultPort();
    } else {
      model.set(PORT_NR, port);
    }
  }

  public String getCustomPort() {
    return model.get(PORT_NR, DEFAULT_PORT);
  }

  private void resetToDefaultPort() {
    model.set(PORT_NR, DEFAULT_PORT);
  }

  public KeyboardTextInput getInputForPortTextField() {
    KeyboardTextInput keyboInput = keyboardFactory.createStringKeyboardInput();
    keyboInput.setInitialValue(getCustomPort());
    return keyboInput;
  }

  public KeyboardInputCallback<String> getCallbackForPortTextField() {
    return new KeyboardInputCallback<String>() {
      @Override
      public void onOk(String value) {
        setHostPort(value);
        view.UpdatePortTextField(value);
      }
    };
  }

  // name helper functions
  public void setName(String name) {
    if ("".equals(name)) {
      resetToDefaultName();
    } else {
      model.set(NAME, name);
    }
  }

  public String getName() {
    return model.get(NAME, DEFAULT_NAME);
  }

  private void resetToDefaultName() {
    model.set(NAME, DEFAULT_NAME);
  }

  public KeyboardTextInput getInputForNameTextField() {
    KeyboardTextInput keyboInput = keyboardFactory.createStringKeyboardInput();
    keyboInput.setInitialValue(getName());
    return keyboInput;
  }

  public KeyboardInputCallback<String> getCallbackForNameTextField() {
    return new KeyboardInputCallback<String>() {
      @Override
      public void onOk(String value) {
        setName(value);
        view.UpdateNameTextField(value);
      }
    };
  }

  public String getControlLoop(ScriptWriter writer) {
    if (programRequested == false) {
      // Request and split the program
      sender = new RequestProgram(getHostIP(), getCustomPort());
      sender.requestAndSplitProgram();

      // Append header to the ur program.
      writer.appendRaw(sender.getHeader());

      programRequested = true;
    }
    return sender.getControlLoop();
  }
}
