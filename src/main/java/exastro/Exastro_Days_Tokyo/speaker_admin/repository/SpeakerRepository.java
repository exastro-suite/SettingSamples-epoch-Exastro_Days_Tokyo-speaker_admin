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

package exastro.Exastro_Days_Tokyo.speaker_admin.repository;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import exastro.Exastro_Days_Tokyo.speaker_admin.repository.config.ConnectionConfig;
import exastro.Exastro_Days_Tokyo.speaker_admin.repository.vo.SpeakerDetailVO;
import exastro.Exastro_Days_Tokyo.speaker_admin.repository.vo.SpeakerVO;

@Repository
public class SpeakerRepository extends BaseRepository {
	
	@Autowired
	public SpeakerRepository(@Qualifier("configSpeaker") ConnectionConfig connectionConfig,
			RestTemplate restTemplate) {
		this.connectionConfig = connectionConfig;
		this.restTemplate = restTemplate;
	}
	
	public List<SpeakerVO> getSpeakerList() {
		
		logger.debug("method called. [ " + Thread.currentThread().getStackTrace()[1].getMethodName() + " ]");
		
		String apiPath = "/api/v1/speaker";
		String apiUrl = connectionConfig.buildBaseUri() + apiPath;
		
		SpeakerVO[] resBody = null;
		try {
			resBody = restTemplate.getForObject(apiUrl, SpeakerVO[].class);
			return Arrays.asList(resBody);
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	public SpeakerDetailVO getSpeakerDetail(int speakerId) {
		
		logger.debug("method called. [ " + Thread.currentThread().getStackTrace()[1].getMethodName() + " ]");
		
		String apiPath = "/api/v1/speaker/{speakerId}";
		String apiUrl = connectionConfig.buildBaseUri() + apiPath;
		
		SpeakerDetailVO resBody = null;
		try {
			logger.debug("restTemplate.getForObject [apiUrl: " + apiUrl + "], [speakerId: " + speakerId + "]");
			
			resBody = restTemplate.getForObject(apiUrl, SpeakerDetailVO.class, speakerId);
			return resBody;
		}
		catch(HttpClientErrorException e) {
			if(e.getStatusCode() == HttpStatus.NOT_FOUND) {
				return null;
			}
			throw e;
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	public String registerSpeaker(SpeakerDetailVO sv) {
		
		logger.debug("method called. [ " + Thread.currentThread().getStackTrace()[1].getMethodName() + " ]");
		
		String apiPath = "/api/v1/speaker";
		String apiUrl = connectionConfig.buildBaseUri() + apiPath;
		
		String resultStr = null;
		
		try {
			
			logger.debug("restTemplate.postForLocation [apiUrl: " + apiUrl + "], [body: " + sv.toString() + "]");
			restTemplate.postForLocation(apiUrl, sv);
			
			resultStr = "{\"result\":\"ok\"}";
			return resultStr;
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	public String updateSpeaker(SpeakerDetailVO sv) {
		logger.debug("method called. [ " + Thread.currentThread().getStackTrace()[1].getMethodName() + " ]");
		
		String apiPath = "/api/v1/speaker/" + sv.getSpeakerId();
		String apiUrl = connectionConfig.buildBaseUri() + apiPath;
		
		String resultStr = null;
		try {
			
			SpeakerDetailVO svo = new SpeakerDetailVO(sv.getSpeakerId(), sv.getSpeakerName(), sv.getSpeakerProfile());
			
			logger.debug("restTemplate.put [apiUrl: " + apiUrl + "], [body: " + svo.toString() + "]");
			restTemplate.put(apiUrl, svo);
			
			resultStr = "{\"result\":\"ok\"}";
			return resultStr;
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	public String deleteSpeaker(int speakerId) {
		
		logger.debug("method called. [ " + Thread.currentThread().getStackTrace()[1].getMethodName() + " ]");
		
		String apiPath = "/api/v1/speaker/{speakerId}";
		String apiUrl = connectionConfig.buildBaseUri() + apiPath;
		
		String resultStr = null;
		try {
			
			logger.debug("restTemplate.getForEntity [apiUrl: " + apiUrl + "], [speakerId: " + speakerId + "]");
			restTemplate.delete(apiUrl, speakerId);
			
			resultStr = "{\"result\":\"ok\"}";
			return resultStr;
		}
		catch(Exception e) {
			throw e;
		}
	}

}
