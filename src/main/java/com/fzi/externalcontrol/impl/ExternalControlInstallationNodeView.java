//-- BEGIN LICENSE BLOCK ----------------------------------------------
// Copyright 2019 FZI Forschungszentrum Informatik
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//  http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
//-- END LICENSE BLOCK ------------------------------------------------

//----------------------------------------------------------------------
/*!\file
*
* \author  Lea Steffen steffen@fzi.de
* \date    2019-04-18
*
*/
//----------------------------------------------------------------------

package com.fzi.externalcontrol.impl;

import com.ur.urcap.api.contribution.installation.swing.SwingInstallationNodeView;
import com.ur.urcap.api.domain.userinteraction.keyboard.KeyboardTextInput;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ExternalControlInstallationNodeView
    implements SwingInstallationNodeView<ExternalControlInstallationNodeContribution> {
  private JTextField textField;

  public ExternalControlInstallationNodeView() {}

  @Override
  public void buildUI(
      JPanel panel, final ExternalControlInstallationNodeContribution contribution) {
    JLabel label = new JLabel("Please setup the remote host's IP: ");
    panel.add(label);
    textField = new JTextField(15);
    textField.setText(contribution.getHostIP());
    textField.setFocusable(false);
    textField.addMouseListener(new MouseAdapter() {
      @Override
      public void mousePressed(MouseEvent e) {
        KeyboardTextInput keyboardInput = contribution.getInputForTextField();
        keyboardInput.show(textField, contribution.getCallbackForTextField());
      }
    });
    panel.add(textField);
  }

  public void UpdateIPTextField(String value) {
    textField.setText(value);
  }
}