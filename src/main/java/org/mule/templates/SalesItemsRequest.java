package org.mule.templates;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.workday.revenue.CurrencyObjectIDType;
import com.workday.revenue.CurrencyObjectType;
import com.workday.revenue.GetProspectsRequestType;
import com.workday.revenue.GetSalesItemsRequestType;
import com.workday.revenue.ProspectRequestCriteriaType;
import com.workday.revenue.ProspectResponseGroupType;
import com.workday.revenue.PutSalesItemRequestType;
import com.workday.revenue.ResponseFilterType;
import com.workday.revenue.SalesItemDataType;
import com.workday.revenue.SalesItemObjectType;
import com.workday.revenue.SalesItemRequestCriteriaType;
import com.workday.revenue.SalesItemRequestReferencesType;
import com.workday.revenue.SalesItemResponseGroupType;

public class SalesItemsRequest {

	public static GetSalesItemsRequestType create(String startDate) throws ParseException, DatatypeConfigurationException {
					
		GetSalesItemsRequestType getSalesItemsRequest = new GetSalesItemsRequestType();		
//		EffectiveAndUpdatedDateTimeDataType dateRangeData = new EffectiveAndUpdatedDateTimeDataType();
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.SECOND, -3);
//		dateRangeData.setUpdatedThrough();
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
//		dateRangeData.setUpdatedFrom(xmlDate(sdf.parse(startDate)));
//		
//		WorkerRequestCriteriaType crit = new WorkerRequestCriteriaType();
//		List<TransactionLogCriteriaType> transactionLogCriteriaData = new ArrayList<TransactionLogCriteriaType>();
//		TransactionLogCriteriaType log = new TransactionLogCriteriaType();
//		log.setTransactionDateRangeData(dateRangeData);
//		
//		transactionLogCriteriaData.add(log );
//		crit.setTransactionLogCriteriaData(transactionLogCriteriaData );
//		getWorkersType.setRequestCriteria(crit );
//		crit.setExcludeInactiveWorkers(true);
//		WorkerResponseGroupType resGroup = new WorkerResponseGroupType();
//		resGroup.setIncludeRoles(true);	
//		resGroup.setIncludePersonalInformation(true);
//		resGroup.setIncludeOrganizations(true);
//		resGroup.setIncludeEmploymentInformation(true);	
//		resGroup.setIncludeUserAccount(true);
//		resGroup.setIncludeTransactionLogData(true);
//		resGroup.setIncludeManagementChainData(true);
//		resGroup.setIncludeReference(true);
		
//		
//		ResponseFilterType responseFilter = new ResponseFilterType();		
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
//		responseFilter.setAsOfEntryDateTime(xmlDate(sdf.parse(startDate)));		
//		getSalesItemsRequest.setResponseFilter(responseFilter );
//		
//		getSalesItemsRequest.setVersion("v13");
//		SalesItemResponseGroupType value = new SalesItemResponseGroupType();
//		value.setIncludeReference(true);
//		getSalesItemsRequest.setResponseGroup(value);
		
		
		return getSalesItemsRequest;
	}
	
	private static XMLGregorianCalendar xmlDate(Date date) throws DatatypeConfigurationException {
		GregorianCalendar gregorianCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
		gregorianCalendar.setTime(date);
		return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
	}		
}
