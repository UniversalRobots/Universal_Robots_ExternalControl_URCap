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

package com.fzi.generateScript.impl;

import com.ur.urcap.api.contribution.ViewAPIProvider;
import com.ur.urcap.api.contribution.installation.ContributionConfiguration;
import com.ur.urcap.api.contribution.installation.CreationContext;
import com.ur.urcap.api.contribution.installation.InstallationAPIProvider;
import com.ur.urcap.api.contribution.installation.swing.SwingInstallationNodeService;
import com.ur.urcap.api.domain.data.DataModel;

import java.util.Locale;

public class GenerateScriptInstallationNodeService
    implements SwingInstallationNodeService<GenerateScriptInstallationNodeContribution,
        GenerateScriptInstallationNodeView> {
  @Override
  public void configureContribution(ContributionConfiguration configuration) {
    // TODO Auto-generated method stub
  }

  @Override
  public String getTitle(Locale locale) {
    return "Generate Script";
  }

  @Override
  public GenerateScriptInstallationNodeView createView(ViewAPIProvider apiProvider) {
    return new GenerateScriptInstallationNodeView();
  }

  @Override
  public GenerateScriptInstallationNodeContribution createInstallationNode(
      InstallationAPIProvider apiProvider, GenerateScriptInstallationNodeView view, DataModel model,
      CreationContext context) {
    return new GenerateScriptInstallationNodeContribution(apiProvider, view, model);
  }
}
