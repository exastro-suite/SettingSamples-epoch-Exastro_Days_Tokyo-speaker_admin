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

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import exastro.Exastro_Days_Tokyo.speaker_admin.repository.SpeakerRepository;
import exastro.Exastro_Days_Tokyo.speaker_admin.repository.vo.SpeakerVO;
import exastro.Exastro_Days_Tokyo.speaker_admin.service.dto.SpeakerDto;

@ExtendWith(MockitoExtension.class)
//@SpringBootTest
public class SpeakerAdminServiceTest {
	
	@Mock   // モックオブジェクトとして使用することを宣言
	private SpeakerRepository repository;
	
	@InjectMocks    // モックオブジェクトの注入
	private SpeakerAdminService speakerAdminService;
	
	@Test
	public void test_getSpeakerList() {
		
		// Mock設定
		when(repository.getSpeakerList()).thenReturn(getSpeakerListMock());
		
		// 対象メソッド実行
		List<SpeakerDto> speakerList = speakerAdminService.getSpeakerList();
		
		// 以下、結果確認
		assertThat(speakerList).hasSize(3);
		
		assertThat(speakerList.get(0).getSpeakerId(), is(1));
		assertThat(speakerList.get(0).getSpeakerName(), is("item1"));
		assertThat(speakerList.get(1).getSpeakerId(), is(2));
		assertThat(speakerList.get(1).getSpeakerName(), is("item2"));
		assertThat(speakerList.get(2).getSpeakerId(), is(3));
		assertThat(speakerList.get(2).getSpeakerName(), is("item3"));
		
	}
	
	// Test Data
	private List<SpeakerVO> getSpeakerListMock() {
		
		List<SpeakerVO> testData = new ArrayList<>();
		
		testData.add(new SpeakerVO(1, "item1"));
		testData.add(new SpeakerVO(2, "item2"));
		testData.add(new SpeakerVO(3, "item3"));
		
		return testData;
	}
}
