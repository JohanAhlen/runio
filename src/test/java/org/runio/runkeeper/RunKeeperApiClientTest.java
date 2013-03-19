package org.runio.runkeeper;

import static junit.framework.Assert.*;
import static org.hamcrest.core.StringContains.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;
import java.util.List;
import org.joda.time.LocalDateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.runio.runkeeper.activity.RunKeeperActivity;
import org.runio.testutil.ResourceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RunKeeperConfig.class })
public class RunKeeperApiClientTest {

    @Autowired
    RestTemplate runKeeperRestTemplate;

    @Autowired
    RunKeeperApiClient subject;

    private MockRestServiceServer mockServer;

    @Before
    public void setUpMockRestServiceServer() {
        this.mockServer =  MockRestServiceServer.createServer(runKeeperRestTemplate);
    }

    @After
    public void verifyMockRestServiceServer() {
        this.mockServer.verify();
    }

    @Test
    public void should_retrieve_summaries() {
        mockServer.expect(requestTo("https://api.runkeeper.com/fitnessActivities"))
                .andRespond(withSuccess(ResourceUtils.readResourceToString("/json/api/fitnessActivities.1.json"), MediaType.APPLICATION_JSON));
        mockServer.expect(requestTo(containsString("https://api.runkeeper.com/fitnessActivities?page=1")))
                .andRespond(withSuccess(ResourceUtils.readResourceToString("/json/api/fitnessActivities.2.json"), MediaType.APPLICATION_JSON));

        List<RunKeeperActivity> result = subject.retrieveAllReducedActivities();

        assertEquals(4, result.size());

        RunKeeperActivity thirdItem = result.get(2);
        assertEquals(3000.0, thirdItem.getTotalDurationInSeconds());
        assertEquals(2400.0, thirdItem.getTotalDistanceInKm());
        assertEquals("API", thirdItem.getEntryMode());
        assertEquals("RunKeeper", thirdItem.getSource());
        assertEquals(new LocalDateTime(2013, 1, 23, 19, 30, 0), thirdItem.getStartTime());
        assertEquals("Running", thirdItem.getType());
        assertEquals(true, thirdItem.getPathAvailable().booleanValue());
        assertEquals("/fitnessActivities/30", thirdItem.getUri());
    }
}
