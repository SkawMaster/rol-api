package es.esky.role.pagination;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;

import es.esky.role.http.ApiHttpHeaders;
import es.esky.role.http.header.LinkHeaderBuilder;
import es.esky.role.http.header.TotalCountHeaderBuilder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasEntry;

@RunWith(MockitoJUnitRunner.class)
public class HateoasPaginationHeadersBuilderTest {
    
    private final static String EXPECTED_LINK = "dummy";
    private final static String EXPECTED_TOTAL = "11";
    
    @InjectMocks
    private HateoasPaginationHeadersBuilder hateoasPaginationHeadersBuilder;
    
    @Mock
    private LinkHeaderBuilder linkHeaderBuilder;
    
    @Mock
    private TotalCountHeaderBuilder totalCountHeaderBuilder;
    
    @Test
    public void addPaginationData_WithNoFirstLastNextPrev_OnlyAddTotalCount() {
        HttpHeaders initialHeaders = new HttpHeaders();
        
        Page<?> page = buildPage(false, false, false, false);
        
        when(totalCountHeaderBuilder.buildTotal(page)).thenReturn(EXPECTED_TOTAL);
        
        HttpHeaders endHeaders = hateoasPaginationHeadersBuilder.addPaginationData(initialHeaders, page);

        assertThat(endHeaders, not(hasKey(ApiHttpHeaders.LINK)));
        assertThat(endHeaders, hasEntry(equalTo(ApiHttpHeaders.TOTAL_COUNT), hasItem(EXPECTED_TOTAL)));
        assertThat(endHeaders, equalTo(initialHeaders));
    }
    
    @Test
    public void addPaginationData_WithFirst_AddTotalCountAndFirstLink() {
        HttpHeaders initialHeaders = new HttpHeaders();
        
        Page<?> page = buildPage(true, false, false, false);
        
        when(totalCountHeaderBuilder.buildTotal(page)).thenReturn(EXPECTED_TOTAL);
        when(linkHeaderBuilder.buildFirst()).thenReturn(EXPECTED_LINK);
        
        HttpHeaders endHeaders = hateoasPaginationHeadersBuilder.addPaginationData(initialHeaders, page);

        assertThat(endHeaders, hasEntry(equalTo(ApiHttpHeaders.LINK), hasItem(EXPECTED_LINK)));
        assertThat(endHeaders, hasEntry(equalTo(ApiHttpHeaders.TOTAL_COUNT), hasItem(EXPECTED_TOTAL)));
        assertThat(endHeaders, equalTo(initialHeaders));
    }
    
    @Test
    public void addPaginationData_WithLast_AddTotalCountAndLastLink() {
        HttpHeaders initialHeaders = new HttpHeaders();
        
        Page<?> page = buildPage(false, true, false, false);
        
        when(totalCountHeaderBuilder.buildTotal(page)).thenReturn(EXPECTED_TOTAL);
        when(linkHeaderBuilder.buildLast(page)).thenReturn(EXPECTED_LINK);
        
        HttpHeaders endHeaders = hateoasPaginationHeadersBuilder.addPaginationData(initialHeaders, page);

        assertThat(endHeaders, hasEntry(equalTo(ApiHttpHeaders.LINK), hasItem(EXPECTED_LINK)));
        assertThat(endHeaders, hasEntry(equalTo(ApiHttpHeaders.TOTAL_COUNT), hasItem(EXPECTED_TOTAL)));
        assertThat(endHeaders, equalTo(initialHeaders));
    }
    
    @Test
    public void addPaginationData_WithNext_AddTotalCountAndNextLink() {
        HttpHeaders initialHeaders = new HttpHeaders();
        
        Page<?> page = buildPage(false, false, true, false);
        
        when(totalCountHeaderBuilder.buildTotal(page)).thenReturn(EXPECTED_TOTAL);
        when(linkHeaderBuilder.buildNext(page)).thenReturn(EXPECTED_LINK);
        
        HttpHeaders endHeaders = hateoasPaginationHeadersBuilder.addPaginationData(initialHeaders, page);

        assertThat(endHeaders, hasEntry(equalTo(ApiHttpHeaders.LINK), hasItem(EXPECTED_LINK)));
        assertThat(endHeaders, hasEntry(equalTo(ApiHttpHeaders.TOTAL_COUNT), hasItem(EXPECTED_TOTAL)));
        assertThat(endHeaders, equalTo(initialHeaders));
    }
    
    @Test
    public void addPaginationData_WithPrev_AddTotalCountAndPrevLink() {
        HttpHeaders initialHeaders = new HttpHeaders();
        
        Page<?> page = buildPage(false, false, false, true);
        
        when(totalCountHeaderBuilder.buildTotal(page)).thenReturn(EXPECTED_TOTAL);
        when(linkHeaderBuilder.buildPrev(page)).thenReturn(EXPECTED_LINK);
        
        HttpHeaders endHeaders = hateoasPaginationHeadersBuilder.addPaginationData(initialHeaders, page);

        assertThat(endHeaders, hasEntry(equalTo(ApiHttpHeaders.LINK), hasItem(EXPECTED_LINK)));
        assertThat(endHeaders, hasEntry(equalTo(ApiHttpHeaders.TOTAL_COUNT), hasItem(EXPECTED_TOTAL)));
        assertThat(endHeaders, equalTo(initialHeaders));
    }
    
    private Page<?> buildPage(boolean hasFirst, boolean hasLast, boolean hasNext, boolean hasPrevious) {
        Page<?> page = mock(Page.class);
        when(page.isFirst()).thenReturn(!hasFirst);
        when(page.isLast()).thenReturn(!hasLast);
        when(page.hasNext()).thenReturn(hasNext);
        when(page.hasPrevious()).thenReturn(hasPrevious);
        when(page.getTotalElements()).thenReturn(11L);
        
        return page;
    }
}
