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
import java.util.Locale;

import com.ur.urcap.api.contribution.ViewAPIProvider;
import com.ur.urcap.api.contribution.program.ContributionConfiguration;
import com.ur.urcap.api.contribution.program.CreationContext;
import com.ur.urcap.api.contribution.program.ProgramAPIProvider;
import com.ur.urcap.api.contribution.program.swing.SwingProgramNodeService;
import com.ur.urcap.api.domain.data.DataModel;

public class GenerateScriptProgramNodeService implements SwingProgramNodeService<GenerateScriptProgramNodeContribution, GenerateScriptProgramNodeView>{

	@Override
	public String getId() {
		return "GenerateScriptNode";
	}

	@Override
	public void configureContribution(ContributionConfiguration configuration) {
		configuration.setChildrenAllowed(false);
	}

	@Override
	public String getTitle(Locale locale) {
		return "Generate Script";
	}

	@Override
	public GenerateScriptProgramNodeView createView(ViewAPIProvider apiProvider) {
		return new GenerateScriptProgramNodeView(apiProvider);
	}

	@Override
	public GenerateScriptProgramNodeContribution createNode(ProgramAPIProvider apiProvider, GenerateScriptProgramNodeView view,
			DataModel model, CreationContext context) {
		return new GenerateScriptProgramNodeContribution(apiProvider, view, model);
	}

}