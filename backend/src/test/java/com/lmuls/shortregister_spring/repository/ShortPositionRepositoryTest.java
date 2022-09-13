package com.lmuls.shortregister_spring.repository;

//public class ShortPositionRepositoryTest {
//
//    @Autowired
//    InstrumentRepository instrumentRepository;
//
//    @Autowired
//    ShorterRepository shorterRepository;
//
//    @Autowired
//    ShortPositionRepository shortPositionRepository;
//
//    @Test
//    public void myTest() {
//        assertTrue(true);
//    }
//
//    @Test
//    public void insertStatementBooleanQueriesTest() {
//        Instrument instrument = instrumentRepository.getById("BMG9156K1018");
//        Shorter shorter = shorterRepository.getById(1);
//
//        assertNotNull(instrument);
//        assertNotNull(shorter);
//
//        boolean bool1 = shortPositionRepository.existsByShorterAndInstrumentAndClosedBefore(shorter, instrument, OffsetDateTime.now());
//        assertTrue(bool1);
//    }
//}
