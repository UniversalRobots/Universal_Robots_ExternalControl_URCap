// -- BEGIN LICENSE BLOCK ----------------------------------------------
// Copyright 2019 FZI Forschungszentrum Informatik
// Created on behalf of Universal Robots A/S
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

public class BuildCommand {
  private final String programName;
  private String commandContent = "";
  private final String prefix = "def ";
  private final String postfix = "end\n";

  /**
   * Create new ScriptCommand with custom name
   * @param name is the custom name (must be alphanumeric and strat with a letter)
   */
  public BuildCommand(String name) {
    this.programName = name + "():\n";
  }

  /**
   * Append URScript line to the ScriptCommand
   * @param command is the line to be appended
   */
  public void appendLine(String command) {
    commandContent += " " + command + "\n";
  }

  @Override
  public String toString() {
    // String command = prefix;
    // command += this.programName;
    String command = this.commandContent;
    // command += this.postfix;
    return "request_program\n";
  }
}
