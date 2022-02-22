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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.text.ParseException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;

import exastro.Exastro_Days_Tokyo.speaker_admin.repository.vo.SpeakerVO;

@SpringBootTest
public class SpeakerRepositoryTest {
	
	private MockRestServiceServer mockServer;
	
	@Autowired
	protected RestTemplate restTemplate;
	
	@Autowired
	private SpeakerRepository speakerRepository;
	
	@Test
	public void test_getSpeakerList() throws JsonProcessingException, ParseException {
		
		// Mock設定
		mockServer = MockRestServiceServer.createServer(restTemplate);
		mockServer.expect(requestTo("http://localhost:8080" + "/api/v1/speaker"))
				.andRespond(withSuccess(getSpeakerListMock3_json(), MediaType.APPLICATION_JSON));
		
		// 対象メソッド実行
		List<SpeakerVO> speakerList = speakerRepository.getSpeakerList();
		
		// 以下、結果確認
		assertThat(speakerList).hasSize(3);
		
		assertThat(speakerList.get(0).getSpeakerId(), is(1));
		assertThat(speakerList.get(0).getSpeakerName(), is("item1"));
		assertThat(speakerList.get(1).getSpeakerId(), is(2));
		assertThat(speakerList.get(1).getSpeakerName(), is("item2"));
		assertThat(speakerList.get(2).getSpeakerId(), is(3));
		assertThat(speakerList.get(2).getSpeakerName(), is("item3"));
		
		mockServer.verify();
	}
	
	// Test Data
	private String getSpeakerListMock3_json() {
		
		return "[ "
				+ " {\"speaker_id\": 1, \"speaker_name\": \"item1\"},"
				+ " {\"speaker_id\": 2, \"speaker_name\": \"item2\"},"
				+ " {\"speaker_id\": 3, \"speaker_name\": \"item3\"}"
				+ " ]";
	}
}
