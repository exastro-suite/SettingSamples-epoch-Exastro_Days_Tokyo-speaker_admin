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

package exastro.Exastro_Days_Tokyo.speaker_admin.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import exastro.Exastro_Days_Tokyo.speaker_admin.repository.vo.SpeakerDetailVO;
import exastro.Exastro_Days_Tokyo.speaker_admin.service.dto.SpeakerDetailDto;
import exastro.Exastro_Days_Tokyo.speaker_admin.service.dto.SpeakerDto;

@Service
public class SpeakerAdminService extends BaseSpeakerService implements SpeakerService {
	
	public SpeakerAdminService() {
		
	}
	
	public List<SpeakerDto> getSpeakerList() {
		
		List<SpeakerDto> speakerList = null;
		
		try {
			speakerList = repository.getSpeakerList()
					.stream()
					.map(s -> new SpeakerDto(s.getSpeakerId(), s.getSpeakerName()))
					.collect(Collectors.toList());
		}
		catch(Exception e) {
			throw e;
		}
		
		return speakerList;
	}
	
	public String registerSpeaker(SpeakerDetailDto ev) {
		
		SpeakerDetailVO speakerDetail = null;
		String resultStr = null;
		try {
			speakerDetail = new SpeakerDetailVO(ev.getSpeakerName(), ev.getSpeakerProfile());
			resultStr = repository.registerSpeaker(speakerDetail);
		}
		catch(Exception e) {
			logger.debug(e.getMessage(), e);
			throw e;
		}
		
		return resultStr;
	}
	
	public String updateSpeaker(SpeakerDetailDto ev) {
		
		SpeakerDetailVO speakerDetail = null;
		String resultStr = null;
		try {
			speakerDetail = new SpeakerDetailVO(ev.getSpeakerId(), ev.getSpeakerName(), ev.getSpeakerProfile());
			resultStr = repository.updateSpeaker(speakerDetail);
		}
		catch(Exception e) {
			logger.debug(e.getMessage(), e);
			throw e;
		}
		
		return resultStr;
	}
	
	public String deleteSpeaker(int speakerId) {
		
		String resultStr = null;
		try {
			resultStr = repository.deleteSpeaker(speakerId);
		}
		catch(Exception e) {
			logger.debug(e.getMessage(), e);
			throw e;
		}
		
		return resultStr;
	}
}
