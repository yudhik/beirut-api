package com.gdn.x.beirut.clientsdk;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.gdn.common.client.GdnRestClientConfiguration;
import com.gdn.common.web.client.GdnBaseRestCrudClient;
import com.gdn.common.web.wrapper.request.SimpleRequestHolder;
import com.gdn.common.web.wrapper.response.GdnBaseRestResponse;
import com.gdn.common.web.wrapper.response.GdnRestListResponse;
import com.gdn.common.web.wrapper.response.GdnRestSingleResponse;
import com.gdn.x.beirut.dto.request.CandidateDTORequest;
import com.gdn.x.beirut.dto.request.ListStringRequest;
import com.gdn.x.beirut.dto.request.PositionDTORequest;
import com.gdn.x.beirut.dto.request.StatusDTORequest;
import com.gdn.x.beirut.dto.response.CandidateDTOResponse;
import com.gdn.x.beirut.dto.response.CandidateDTOResponseWithoutDetail;
import com.gdn.x.beirut.dto.response.CandidatePositionDTOResponse;
import com.gdn.x.beirut.dto.response.CandidatePositionSolrDTOResponse;
import com.gdn.x.beirut.dto.response.CandidateWithPositionsDTOResponse;import com.gdn.x.beirut.dto.response.PositionDTOResponse;
import com.gdn.x.beirut.dto.response.PositionDetailDTOResponse;

public class BeirutApiClient extends GdnBaseRestCrudClient {
	private static final String JSON_TYPE = "application/json";

	public BeirutApiClient(GdnRestClientConfiguration clientConfig, String contextPath) {
		super(clientConfig);
		setContextPath(contextPath);
	}

	private URI generateURI(String path, String requestId, String username, Map<String, String> additionalParameterMap)
			throws Exception {
		String location = getContextPath() + path;
		return getHttpClientHelper().getURI(getClientConfig().getHost(), getClientConfig().getPort(), location,
				getMandatoryParameter(requestId, username), additionalParameterMap);
	}

	public GdnBaseRestResponse applyNewPosition(String requestId, String username, String idCandidate,
			ListStringRequest listPositionIdStrings) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("idCandidate", idCandidate);
		map.put("listPositionIdStrings", String.valueOf(listPositionIdStrings));
		URI uri = generateURI("/api/candidate/applyNewPosition", requestId, username, map);
		return invokePost(uri, CandidateDTORequest.class, idCandidate);
	}

	public GdnBaseRestResponse deleteCandidate(String requestId, String username, String id) throws Exception {
		SimpleRequestHolder request = new SimpleRequestHolder(id);
		URI uri = generateURI("/api/candidate/deleteCandidate", requestId, username, null);
		return invokePost(uri, SimpleRequestHolder.class, request);
	}

	public GdnRestListResponse<CandidateDTOResponse> findCandidateByCreatedDateBetweenAndStoreId(String requestId,
			String username, Long start, Long end, int page, int size) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("start", String.valueOf(start));
		map.put("end", String.valueOf(end));
		map.put("page", String.valueOf(page));
		map.put("size", String.valueOf(size));
		URI uri = generateURI("/api/candidate/findCandidateByCreatedDateBetweenAndStoreId", requestId, username, map);
		return invokeGetSummary(uri, CandidateDTOResponse.class);
	}

	public GdnRestSingleResponse<CandidateDTOResponse> findCandidateByEmailAddressAndStoreId(String requestId,
			String username, String emailAddress) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("emailAddress", String.valueOf(emailAddress));
		URI uri = generateURI("/api/candidate/findCandidateByEmailAddressAndStoreId", requestId, username, map);
		return invokeGetSingle(uri, CandidateDTOResponse.class);
	}

	public GdnRestListResponse<CandidateDTOResponse> findCandidateByFirstNameContainAndStoreId(String requestId,
			String username, String firstName, int page, int size) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("firstName", firstName);
		map.put("page", String.valueOf(page));
		map.put("size", String.valueOf(size));
		URI uri = generateURI("/api/candidate/findCandidateByFirstNameContainAndStoreId", requestId, username, map);
		return invokeGetSummary(uri, CandidateDTOResponse.class);
	}

	public GdnRestSingleResponse<CandidateWithPositionsDTOResponse> findCandidateByIdAndStoreIdEager(String requestId,
			String username, String idCandidate) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("idCandidate", String.valueOf(idCandidate));
		URI uri = generateURI("/api/candidate/findCandidateByIdAndStoreIdEager", requestId, username, map);
		return invokeGetSingle(uri, CandidateWithPositionsDTOResponse.class);
	}

	public GdnRestSingleResponse<CandidateDTOResponse> findCandidateByIdAndStoreIdLazy(String requestId,
			String username, String idCandidate) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("idCandidate", String.valueOf(idCandidate));
		URI uri = generateURI("/api/candidate/findCandidateByIdAndStoreIdLazy", requestId, username, map);
		return invokeGetSingle(uri, CandidateDTOResponse.class);
	}

	public GdnRestListResponse<CandidateDTOResponse> findCandidateByLastNameContainAndStoreId(String requestId,
			String username, String lastName, int page, int size) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("lastName", lastName);
		map.put("page", String.valueOf(page));
		map.put("size", String.valueOf(size));
		URI uri = generateURI("/api/candidate/findCandidateByLastNameContainAndStoreId", requestId, username, map);
		return invokeGetSummary(uri, CandidateDTOResponse.class);
	}

	public GdnRestListResponse<CandidateDTOResponse> findCandidateByPhoneNumberContainAndStoreId(String requestId,
			String username, String phoneNumber, int page, int size) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("phoneNumber", phoneNumber);
		map.put("page", String.valueOf(page));
		map.put("size", String.valueOf(size));
		URI uri = generateURI("/api/candidate/findCandidateByPhoneNumberContainAndStoreId", requestId, username, map);
		return invokeGetSummary(uri, CandidateDTOResponse.class);
	}

	// dipertanyakan
	// harusnya ngeluarin byte []
	public void findCandidateDetailAndStoreId(String requestId, String username, String id) throws Exception {
		SimpleRequestHolder request = new SimpleRequestHolder(id);
		URI uri = generateURI("/api/candidate/findCandidateDetailAndStoreId", requestId, username, null);
		// return invokeGetSingle(uri, Byte.class, request);
	}

	public GdnRestListResponse<CandidateDTOResponseWithoutDetail> getAllCandidateByStoreIdAndMarkForDeleteWithPageable(
			String requestId, String username, boolean markForDelete, int page, int size) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("markForDelete", String.valueOf(markForDelete));
		map.put("page", String.valueOf(page));
		map.put("size", String.valueOf(size));
		URI uri = generateURI("/api/candidate/getAllCandidatesByStoreIdAndMarkForDeleteWithPageable", requestId,
				username, map);
		return invokeGetSummary(uri, CandidateDTOResponseWithoutDetail.class);
	}

	public GdnRestListResponse<CandidateDTOResponseWithoutDetail> getAllCandidateByStoreIdWithPageable(String requestId,
			String username, int page, int size) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("page", String.valueOf(page));
		map.put("size", String.valueOf(size));
		URI uri = generateURI("/api/candidate/getAllCandidatesByStoreIdWithPageable", requestId, username, map);
		return invokeGetSummary(uri, CandidateDTOResponseWithoutDetail.class);
	}

	public GdnRestListResponse<CandidatePositionSolrDTOResponse> getCandidatePositionBySolrQuery(String requestId,
			String username, String query, int page, int size) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("query", query);
		map.put("page", String.valueOf(page));
		map.put("size", String.valueOf(size));
		URI uri = generateURI("/api/candidate/getCandidatePositionBySolrQuery", requestId, username, map);
		return invokeGetSummary(uri, CandidatePositionSolrDTOResponse.class);
	}

	public GdnRestSingleResponse<CandidatePositionDTOResponse> getCandidatePositionDetailByStoreIdWithLogs(
			String requestId, String username, String query, String idCandidate, String idPosition) throws Exception {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("idCandidate", idCandidate);
		map.put("idPosition", idPosition);
		URI uri = generateURI("/api/candidate/getCandidatePositionDetailByStoreIdWithLogs", requestId, username, map);
		return invokeGetSingle(uri, CandidatePositionDTOResponse.class);
	}

	public GdnBaseRestResponse insertNewCandidate(String requestId, String username, String candidateDTORequestString,
			MultipartFile file) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("candidateDTORequestString", candidateDTORequestString);
		URI uri = generateURI("/api/candidate/insertNewCandidate", requestId, username, map);
		return invokePost(uri, MultipartFile.class, file);
	}

	public GdnBaseRestResponse markForDelete(String requestId, String username, ListStringRequest idsRequest) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("idsRequest", String.valueOf(idsRequest));
		URI uri = generateURI("/api/candidate/markForDelete", requestId, username, map);
		return invokePost(uri, ListStringRequest.class, idsRequest);
	}

	public GdnBaseRestResponse updateCandidateDetail(String requestId, String username, String idCandidate,
			MultipartFile file) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("idCandidate", idCandidate);
		URI uri = generateURI("/api/candidate/updateCandidateDetail", requestId, username, map);
		return invokePost(uri, Multipart.class, file);
	}

	public GdnBaseRestResponse updateCandidatesStatus(String requestId, String username, StatusDTORequest status,
			String idPosition, ListStringRequest idCandidates) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("status", String.valueOf(status));
		map.put("idPosition", idPosition);
		map.put("idCandidates", String.valueOf(idCandidates));
		URI uri = generateURI("/api/candidate/updateCandidateStatus", requestId, username, map);
		return invokePost(uri, CandidateDTOResponse.class, idCandidates);
	}
	
	public GdnBaseRestResponse deletePosition (String requestId, String username, ListStringRequest idsToDelete){
		SimpleRequestHolder request = new SimpleRequestHolder(idsToDelete.toString());
		URI uri = generateURI("/api/position/deletePosition", requestId, username, null);
		return invokePost(uri, SimpleRequestHolder.class, request);
	}
	
	public GdnRestListResponse<PositionDTOResponse> getAllPositionByStoreId (String requestId, String username){
		URI uri = generateURI("/api/candidate/getAllPosition", requestId, username, null);
		return invokePostSummary(uri, PositionDTOResponse.class);
	}
	
	public GdnRestListResponse<PositionDTOResponse> getAllPositionWithPageable (String requestId,
			String username, int page, int size){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("page", String.valueOf(page));
		map.put("size", String.valueOf(size));
		URI uri = generateURI("/api/position/getAllPositionWithPageable", requestId, username, map);
		return invokeGetSummary(uri, PositionDTOResponse.class);
	}
	
	public GdnRestListResponse<PositionDTOResponse> getPositionByStoreIdAndMarkForDelete (String requestId,
			String username, boolean markForDelete){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("markForDelete", String.valueOf(markForDelete));
		URI uri = generateURI("/api/position/getPositionByStoreIdAndMarkForDelete", requestId, username, map);
		return invokeGetSummary(uri, PositionDTOResponse.class);
	}
	
	public GdnRestListResponse<PositionDTOResponse> getPositionByTitle (String requestId,
			String username, String title){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("title", title);
		URI uri = generateURI("/api/position/getPositionByTitle", requestId, username, map);
		return invokeGetSummary(uri, PositionDTOResponse.class);
	}
	
	public GdnRestListResponse<PositionDetailDTOResponse> getPositionDetailById (String requestId,
			String username, String id){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		URI uri = generateURI("/api/position/getPositionDetail", requestId, username, map);
		return invokeGetSummary(uri, PositionDetailDTOResponse.class);
	}
	
	public GdnRestSingleResponse<PositionDTOResponse> insertNewPosition (String requestId,
			String username, PositionDTORequest positionDTORequest){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("positionDTORequest", String.valueOf(positionDTORequest));
		URI uri = generateURI("/api/position/insertNewPosition", requestId, username, map);
		return invokePostSingle(uri, PositionDTOResponse.class);
	}
	
	public GdnBaseRestResponse updatePosition (String requestId,
			String username, String id, PositionDTORequest positionDTORequest){
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("positionDTORequest", String.valueOf(positionDTORequest));
		URI uri = generateURI("/api/position/updatePosition", requestId, username, map);
		return invokePostSingle(uri, PositionDTOResponse.class);
	}
}
