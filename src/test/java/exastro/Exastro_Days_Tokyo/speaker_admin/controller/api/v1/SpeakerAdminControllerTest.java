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

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import exastro.Exastro_Days_Tokyo.speaker_admin.service.SpeakerService;
import exastro.Exastro_Days_Tokyo.speaker_admin.service.dto.SpeakerDto;

@ExtendWith(SpringExtension.class)
public class SpeakerAdminControllerTest {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	MockMvc mockMvc;
	
	@Mock   // モックオブジェクトとして使用することを宣言
	protected SpeakerService service;
	
	@InjectMocks    // モックオブジェクトの注入
	private SpeakerAdminController speakerAdminController;
	
	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(speakerAdminController).build();
	}
	
	@Test
	public void test_getSpeakerList() throws Exception {
		
		// Mock設定
		when(service.getSpeakerList()).thenReturn(getSpeakerListMock());
		
		// 対象メソッド実行
		MvcResult mvcResult = mockMvc.perform(get("/api/v1/speaker"))
				// 以下、結果確認
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(3)))
				
				.andExpect(jsonPath("$[0].speaker_id").value(1))
				.andExpect(jsonPath("$[0].speaker_name").value("item1"))
				.andExpect(jsonPath("$[1].speaker_id").value(2))
				.andExpect(jsonPath("$[1].speaker_name").value("item2"))
				.andExpect(jsonPath("$[2].speaker_id").value(3))
				.andExpect(jsonPath("$[2].speaker_name").value("item3"))
				
				.andReturn();
		
		logger.info("external response : {}", mvcResult.getResponse().getContentAsString());
		
	}
	
	// Test Data
	private List<SpeakerDto> getSpeakerListMock() throws ParseException {
		
		List<SpeakerDto> testData = new ArrayList<>();
		
		testData.add(new SpeakerDto(1, "item1"));
		testData.add(new SpeakerDto(2, "item2"));
		testData.add(new SpeakerDto(3, "item3"));
		
		return testData;
	}
}
