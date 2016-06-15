package com.gdn.x.beirut.services;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Hibernate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.gdn.x.beirut.dao.CandidateDAO;
import com.gdn.x.beirut.dao.PositionDAO;
import com.gdn.x.beirut.entities.Candidate;
import com.gdn.x.beirut.entities.CandidateDetail;
import com.gdn.x.beirut.entities.CandidatePosition;
import com.gdn.x.beirut.entities.Position;
import com.gdn.x.beirut.entities.Status;
import com.gdn.x.beirut.entities.StatusLog;

public class CandidateServiceTest {

  private static final String ID = "ID";

  private static final String STORE_ID = "MYID";

  private static final String FIRST_NAME = "RIZAL";

  private static final String LIKE_FIRST_NAME = "IZA";

  private static final String LIKE_LAST_NAME = "AHRI";

  private static final String LAST_NAME = "FAHRI";

  private static final Status STATUS = Status.APPLY;

  @Mock
  private CandidateDAO candidateDao;

  @Mock
  private PositionDAO positionDao;

  @InjectMocks
  private CandidateServiceImpl candidateService;

  private Candidate candidate;

  private Candidate markForDeleteCandidate;

  private CandidateDetail candidateDetail;

  private Candidate candidateWithDetail;

  private List<Candidate> candidates;

  private List<Candidate> candidateRanges;

  private Position position;

  @Before
  public void initialize() {
    initMocks(this);
    this.position = new Position();
    this.position.setId(ID);
    this.position.setStoreId(STORE_ID);
    this.position.setTitle("Position Title");

    this.candidate = new Candidate();
    this.candidate.setId(ID);
    this.candidate.setStoreId(STORE_ID);
    this.candidate.setFirstName(FIRST_NAME);
    this.candidate.setLastName(LAST_NAME);

    this.markForDeleteCandidate = new Candidate();
    this.markForDeleteCandidate.setId(ID);
    this.markForDeleteCandidate.setStoreId(STORE_ID);
    this.markForDeleteCandidate.setFirstName(FIRST_NAME);
    this.markForDeleteCandidate.setLastName(LAST_NAME);
    this.markForDeleteCandidate.setMarkForDelete(true);

    this.candidateDetail = new CandidateDetail();
    this.candidateDetail.setId(ID);


    this.candidateWithDetail = new Candidate();
    this.candidateWithDetail.setId(ID);
    this.candidateWithDetail.setStoreId(STORE_ID);
    this.candidateWithDetail.setFirstName(FIRST_NAME);
    this.candidateWithDetail.setLastName(LAST_NAME);
    this.candidateWithDetail.setCandidateDetail(this.candidateDetail);

    this.candidates = new ArrayList<Candidate>();
    this.candidates.add(this.candidate);

    when(this.candidateDao.findByFirstNameLike(LIKE_FIRST_NAME)).thenReturn(this.candidates);
    when(this.candidateDao.findByLastNameLike(LIKE_LAST_NAME)).thenReturn(this.candidates);
    when(this.candidateDao.findOne(ID)).thenReturn(this.candidate);
    when(this.candidateDao.save(this.candidate)).thenReturn(this.candidate);
    candidateRanges = new ArrayList<>();
    GregorianCalendar start = new GregorianCalendar(2016, 1, 1);
    GregorianCalendar create = new GregorianCalendar(2016, 2, 1);
    GregorianCalendar end = new GregorianCalendar(2016, 6, 1);
    for (int i = 0; i < 10; i++) {
      Candidate newCandidate = new Candidate();
      newCandidate.setId(ID + " " + i);
      newCandidate.setStoreId(STORE_ID);
      newCandidate.setEmailAddress("egaprianto" + i + "@asd.com");
      newCandidate.setFirstName("Ega");
      newCandidate.setLastName("Prianto");
      newCandidate.setPhoneNumber("123456789" + i);
      newCandidate.setCreatedDate(create.getTime());
      CandidateDetail candDetail = new CandidateDetail();
      candDetail.setContent(("ini PDF" + i).getBytes());
      newCandidate.setCandidateDetail(candDetail);
      candidates.add(newCandidate);
    }

    Candidate newCandidate = new Candidate();
    newCandidate.setId(ID);
    newCandidate.setStoreId(STORE_ID);
    newCandidate.setEmailAddress("egaprianto@asd.com");
    newCandidate.setFirstName("Ega");
    newCandidate.setLastName("Prianto");
    newCandidate.setPhoneNumber("1234567890");
    newCandidate.setCreatedDate(create.getTime());
    CandidateDetail candDetail = new CandidateDetail();
    candDetail.setContent(("ini PDF").getBytes());
    newCandidate.setCandidateDetail(candDetail);
    candidateRanges.add(newCandidate);


    for (int i = 0; i < 10; i++) {
      Candidate toBeDeletedCandidate = new Candidate();
      toBeDeletedCandidate.setId(ID + " " + i);
      toBeDeletedCandidate.setStoreId(STORE_ID);
      toBeDeletedCandidate.setEmailAddress("egaprianto" + i + "@asd.com");
      toBeDeletedCandidate.setFirstName("Ega");
      toBeDeletedCandidate.setLastName("Prianto");
      toBeDeletedCandidate.setPhoneNumber("123456789" + i);
      toBeDeletedCandidate.setCreatedDate(create.getTime());
      toBeDeletedCandidate.setMarkForDelete(false);
      CandidateDetail candidateDetail = new CandidateDetail();
      candidateDetail.setContent(("ini PDF" + i).getBytes());
      toBeDeletedCandidate.setCandidateDetail(candidateDetail);
      candidates.add(toBeDeletedCandidate);
      Mockito.when(this.candidateDao.findByIdAndMarkForDelete(ID + " " + i, false))
          .thenReturn(toBeDeletedCandidate);
    }

    when(this.candidateDao.findByCreatedDateBetween(start.getTime(), end.getTime()))
        .thenReturn(candidates);
  }

  @After
  public void noMoreTransaction() {
    verifyNoMoreInteractions(this.candidateDao);
  }


  @Test
  public void testApplyNewPosition() throws Exception {
    Assert.assertTrue(this.candidateDao.findOne(ID).equals(this.candidate));
    this.candidateService.applyNewPosition(this.candidate, this.position);
    Candidate cand = this.candidate;
    cand.getCandidatePositions().add(new CandidatePosition(this.candidate, this.position));
    Mockito.verify(this.candidateDao, Mockito.times(2)).findOne(ID);
    Mockito.verify(this.candidateDao, times(1)).save(cand);
  }

  @Test
  public void testGetAllCandidates() {
    Candidate cand1 = new Candidate();
    cand1.setId(ID);
    cand1.setStoreId(STORE_ID);
    Candidate cand2 = new Candidate();
    cand2.setId(ID);
    cand2.setStoreId(STORE_ID);
    List<Candidate> candidates = new ArrayList<Candidate>();
    candidates.add(cand1);
    candidates.add(cand2);
    when(this.candidateDao.findAll()).thenReturn(candidates);

    // Black Box Test
    Assert.assertTrue(this.candidateDao.findAll() == candidates);
    // White Box Test
    this.candidateService.getAllCandidates();
    verify(this.candidateDao, times(2)).findAll();
  }

  @Test
  public void testGetCandidate() throws Exception {
    // Black Box Test
    Assert.assertTrue(this.candidateDao.findOne(ID) == this.candidate);
    // White Box Test
    this.candidateService.getCandidate(ID);
    verify(this.candidateDao, times(2)).findOne(ID);
  }

  @Test
  public void testGetCandidateDetail() throws Exception {
    // Black Box Test
    assertTrue(this.candidateDao.findOne(ID).equals(this.candidate));
    // White Box Test
    this.candidateService.getCandidateDetail(ID);
    verify(this.candidateDao, times(2)).findOne(ID);
  }

  @Test
  public void testMarkForDelete() throws Exception {
    Mockito.when(this.candidateDao.findByIdAndMarkForDelete(ID, false)).thenReturn(candidate);

    this.candidateService.markForDelete(ID);
    verify(this.candidateDao, times(1)).findByIdAndMarkForDelete(Mockito.anyString(),
        Mockito.eq(false));
    verify(this.candidateDao, times(1)).save(Mockito.any(Candidate.class));
  }

  @Test
  public void testMarkForDeleteBulk() throws Exception {
    List<String> ids = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      String id = ID + " " + i;
      ids.add(id);
    }
    this.candidateService.markForDelete(ids);
    // Black Box Test
    // White Box Test
    verify(this.candidateDao, times(10)).findByIdAndMarkForDelete(Mockito.anyString(),
        Mockito.eq(false));
    final Candidate candidate = this.candidate;
    candidate.setMarkForDelete(true);
    verify(this.candidateDao, times(10)).save(Mockito.any(Candidate.class));
  }

  @Test
  public void testSave() throws Exception {
    // Black Box Test
    assertTrue(this.candidateDao.save(this.candidate).equals(this.candidate));
    // White Box Test
    this.candidateService.createNew(this.candidate, this.position);
    CandidatePosition candidatePosition = new CandidatePosition();
    candidatePosition.setCandidate(candidate);
    candidatePosition.setPosition(position);
    candidate.getCandidatePositions().add(candidatePosition);
    verify(this.candidateDao, times(2)).save(this.candidate);
  }

  @Test
  public void testSearchByCreatedDateBetween() {
    GregorianCalendar start = new GregorianCalendar(2016, 1, 1);
    GregorianCalendar end = new GregorianCalendar(2016, 6, 1);

    List<Candidate> result =
        this.candidateService.searchByCreatedDateBetween(start.getTime(), end.getTime());
    // Black Box Test
    Assert.assertTrue(result.equals(candidates));
    // White Box Test
    verify(this.candidateDao, times(1)).findByCreatedDateBetween(start.getTime(), end.getTime());
  }

  @Test
  public void testSearchByFirstName() {
    // Black Box Test
    Assert
        .assertTrue(this.candidateDao.findByFirstNameLike(LIKE_FIRST_NAME).equals(this.candidates));
    // White Box Test
    this.candidateService.searchByFirstName(LIKE_FIRST_NAME);
    verify(this.candidateDao, times(2)).findByFirstNameLike(LIKE_FIRST_NAME);
  }

  @Test
  public void testSearchByLastName() {
    // Black Box Test
    Assert.assertTrue(this.candidateDao.findByLastNameLike(LIKE_LAST_NAME).equals(this.candidates));
    // White Box Test (CEK PEMANGGILAN)
    this.candidateService.searchByLastName(LIKE_LAST_NAME);
    verify(this.candidateDao, times(2)).findByLastNameLike(LIKE_LAST_NAME);
  }

  @Test
  public void testSearchCandidateByEmailAddress() {
    List<Candidate> res = new ArrayList<>();
    for (Candidate candidate : candidateRanges) {
      if (candidate.getEmailAddress().equals("egaprianto@asd.com")) {
        res.add(candidate);
      }
    }
    when(this.candidateDao.findByEmailAddress("egaprianto@asd.com")).thenReturn(res);
    List<Candidate> result =
        this.candidateService.searchCandidateByEmailAddress("egaprianto@asd.com");
    // Black Box Test
    Assert.assertTrue(result.equals(res));
    // White Box Test
    verify(this.candidateDao, times(1)).findByEmailAddress("egaprianto@asd.com");
  }

  @Test
  public void testSearchCandidateByPhoneNumber() {
    List<Candidate> res = new ArrayList<>();
    for (Candidate candidate : candidateRanges) {
      if (candidate.getPhoneNumber().equals("1234567890")) {
        res.add(candidate);
      }
    }
    when(this.candidateDao.findByPhoneNumber("1234567890")).thenReturn(res);
    List<Candidate> result = this.candidateService.searchCandidateByPhoneNumber("1234567890");
    // Black Box Test
    Assert.assertTrue(result.equals(res));
    // White Box Test
    verify(this.candidateDao, times(1)).findByPhoneNumber("1234567890");
  }

  @Test
  public void testSearchCandidateByPhoneNumberLike() {
    List<Candidate> res = new ArrayList<>();
    for (Candidate candidate : candidateRanges) {
      if (candidate.getPhoneNumber().contains("123456789")) {
        res.add(candidate);
      }
    }
    when(this.candidateDao.findByPhoneNumberLike("123456789")).thenReturn(res);
    List<Candidate> result = this.candidateService.searchCandidateByPhoneNumberLike("123456789");
    // Black Box Test
    Assert.assertTrue(result.equals(res));
    // White Box Test
    verify(this.candidateDao, times(1)).findByPhoneNumberLike("123456789");
  }

  @Test
  public void testUpdateCandidateStatus() throws Exception {
    when(this.positionDao.findOne(ID)).thenReturn(this.position);
    Candidate testCandidate = candidate;
    this.candidateService.updateCandidateStatus(testCandidate, position, STATUS);
    verify(this.candidateDao, times(1)).findOne(ID);
    verify(this.positionDao, times(1)).findOne(ID);
    //
    Hibernate.initialize(testCandidate.getCandidatePositions());
    testCandidate.getCandidatePositions().stream()
        .filter(candidatePosition -> candidatePosition.getPosition().equals(position))
        .forEach(candidatePosition -> {
          candidatePosition.getStatusLogs().add(new StatusLog(candidatePosition, STATUS));
          candidatePosition.setStatus(STATUS); // add missing setter zal
        });
    //
    verify(this.candidateDao, times(1)).save(testCandidate);
  }


  // public void testUpdateCandidateStatusBulk(List<Candidate> candidates, Position position,
  // Status status) throws Exception {
  // @Test
  // public void testUpdateCandidateStatusBulk(){
  //
  // }


}
