package com.example.boilerplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.example.boilerplate.data.entity.DataEntity;
import com.example.boilerplate.data.repository.DataRepository;
import com.example.boilerplate.data.service.DataService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;


@SpringBootTest
class BoilerplateApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(BoilerplateApplicationTests.class);
    private final DataService dataService;
    @MockitoBean
    private DataRepository dataRepository;

    @Autowired
    BoilerplateApplicationTests(DataService dataService) {
        this.dataService = dataService;
    }

    @Test
    void testService() {
        // given: 데이터 반환을 mock 처리
        DataEntity entity1 = new DataEntity();
        DataEntity entity2 = new DataEntity();
        when(dataRepository.findAll()).thenReturn(List.of(entity1, entity2));

        // when: 서비스 메서드 호출
        List<DataEntity> asd = dataService.getAll();

        // then: 리스트 크기가 2 이상인지 확인
        assertThat(asd.size()).isGreaterThanOrEqualTo(2); // 크기가 2 이상이면 테스트 통과
    }

}
