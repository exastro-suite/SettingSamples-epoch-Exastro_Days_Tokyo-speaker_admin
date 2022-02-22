/*   Copyright 2022 NEC Corporation
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package exastro.Exastro_Days_Tokyo.speaker_admin.controller.api.v1;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import exastro.Exastro_Days_Tokyo.speaker_admin.controller.api.v1.form.SpeakerDetailForm;
import exastro.Exastro_Days_Tokyo.speaker_admin.controller.api.v1.form.SpeakerForm;
import exastro.Exastro_Days_Tokyo.speaker_admin.service.dto.SpeakerDetailDto;

@RestController
@RequestMapping("/api/v1/speaker")
public class SpeakerAdminController extends BaseSpeakerController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public SpeakerAdminController() {
		
	}
	
	@GetMapping("")
	public List<SpeakerForm> speakerList() {
		logger.debug("method called. [ " + Thread.currentThread().getStackTrace()[1].getMethodName() + " ]");	
		
		List<SpeakerForm> speakerList = null;
		
		try {
			speakerList = service.getSpeakerList()
					.stream()
					.map(s -> new SpeakerForm(s.getSpeakerId(), s.getSpeakerName()))
					.collect(Collectors.toList());
		}
		catch(Exception e) {
			logger.debug(e.getMessage(), e);
			throw e;
		}
		
		return speakerList;
	}
	
	//登壇者登録
	@RequestMapping(path = "", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED) 
	public String registerSpeaker(@RequestBody SpeakerDetailForm sf) {
		
		logger.debug("method called. [ " + Thread.currentThread().getStackTrace()[1].getMethodName() + " ]");
		
		String resultStr = null;
		try {
			SpeakerDetailDto speakerDetail = new SpeakerDetailDto(sf.getSpeakerName(), sf.getSpeakerProfile());
			
			resultStr = service.registerSpeaker(speakerDetail);
		}
		catch(Exception e) {
			logger.debug(e.getMessage(), e);
			throw e;
		}
		
		return resultStr;
	}
	
	//登壇者更新
	@PutMapping("/{speakerId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public String updateSpeaker(@PathVariable(value = "speakerId") @Validated int speakerId, @RequestBody SpeakerDetailForm sf) {
		
		logger.debug("method called. [ " + Thread.currentThread().getStackTrace()[1].getMethodName() + " ]");
		
		String resultStr = null;
		try {
			// validate data
			if(speakerId != sf.getSpeakerId()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid data.");
			}
			
			SpeakerDetailDto speakerDetail = new SpeakerDetailDto(speakerId, sf.getSpeakerName(), sf.getSpeakerProfile());
			
			resultStr = service.updateSpeaker(speakerDetail);
		
		}
		catch(Exception e) {
			logger.debug(e.getMessage(), e);
			throw e;
		}
		
		return resultStr;
	}
	
	//登壇者削除
	@DeleteMapping("/{speakerId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public String deleteSpeaker(@PathVariable(value = "speakerId") @Validated int speakerId) {
		
		logger.debug("method called. [ " + Thread.currentThread().getStackTrace()[1].getMethodName() + " ]");
		
		String resultStr = null;
		try {
			resultStr = service.deleteSpeaker(speakerId);
		
		}
		catch(Exception e) {
			logger.debug(e.getMessage(), e);
			throw e;
		}
		
		return resultStr;
	}

}
