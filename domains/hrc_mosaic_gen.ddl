DOMAIN SHAREWORK_HRC_MOSAIC_GEN {

	TEMPORAL_MODULE temporal_module = [0, 7000], 100;

	COMP_TYPE SingletonStateVariable GoalType ( DoMosaic(), Idle() ) { 

		VALUE Idle() [1, +INF]
		MEETS {
			DoMosaic();
		}

		VALUE DoMosaic() [1, +INF]
		MEETS {
			Idle();
		}

	}

	COMP_TYPE SingletonStateVariable MosaicProcessType ( DoRow1(),  DoRow2(),  DoRow3(),  DoRow4(),  DoRow5(),  Idle() ) {

		VALUE DoRow1() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoRow2() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoRow3() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoRow4() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoRow5() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE Idle() [1, +INF]
		MEETS {
			DoRow1();
			DoRow2();
			DoRow3();
			DoRow4();
			DoRow5();
		}

	}

	COMP_TYPE SingletonStateVariable AssemblyProcessType ( DoCellA1(),  DoCellB1(),  DoCellC1(),  DoCellD1(),  DoCellE1(),  DoCellF1(),  DoCellG1(),  DoCellH1(),  DoCellI1(),  DoCellJ1(),  DoCellA2(),  DoCellB2(),  DoCellC2(),  DoCellD2(),  DoCellE2(),  DoCellF2(),  DoCellG2(),  DoCellH2(),  DoCellI2(),  DoCellJ2(),  DoCellA3(),  DoCellB3(),  DoCellC3(),  DoCellD3(),  DoCellE3(),  DoCellF3(),  DoCellG3(),  DoCellH3(),  DoCellI3(),  DoCellJ3(),  DoCellA4(),  DoCellB4(),  DoCellC4(),  DoCellD4(),  DoCellE4(),  DoCellF4(),  DoCellG4(),  DoCellH4(),  DoCellI4(),  DoCellJ4(),  DoCellA5(),  DoCellB5(),  DoCellC5(),  DoCellD5(),  DoCellE5(),  DoCellF5(),  DoCellG5(),  DoCellH5(),  DoCellI5(),  DoCellJ5(),  Idle() ) {

		VALUE DoCellA1() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellB1() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellC1() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellD1() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellE1() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellF1() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellG1() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellH1() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellI1() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellJ1() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellA2() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellB2() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellC2() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellD2() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellE2() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellF2() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellG2() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellH2() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellI2() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellJ2() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellA3() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellB3() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellC3() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellD3() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellE3() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellF3() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellG3() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellH3() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellI3() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellJ3() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellA4() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellB4() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellC4() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellD4() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellE4() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellF4() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellG4() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellH4() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellI4() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellJ4() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellA5() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellB5() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellC5() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellD5() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellE5() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellF5() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellG5() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellH5() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellI5() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE DoCellJ5() [1, +INF]
		MEETS {
			Idle();
		}

		VALUE Idle() [1, +INF]
		MEETS {
			DoCellA1();
			DoCellB1();
			DoCellC1();
			DoCellD1();
			DoCellE1();
			DoCellF1();
			DoCellG1();
			DoCellH1();
			DoCellI1();
			DoCellJ1();
			DoCellA2();
			DoCellB2();
			DoCellC2();
			DoCellD2();
			DoCellE2();
			DoCellF2();
			DoCellG2();
			DoCellH2();
			DoCellI2();
			DoCellJ2();
			DoCellA3();
			DoCellB3();
			DoCellC3();
			DoCellD3();
			DoCellE3();
			DoCellF3();
			DoCellG3();
			DoCellH3();
			DoCellI3();
			DoCellJ3();
			DoCellA4();
			DoCellB4();
			DoCellC4();
			DoCellD4();
			DoCellE4();
			DoCellF4();
			DoCellG4();
			DoCellH4();
			DoCellI4();
			DoCellJ4();
			DoCellA5();
			DoCellB5();
			DoCellC5();
			DoCellD5();
			DoCellE5();
			DoCellF5();
			DoCellG5();
			DoCellH5();
			DoCellI5();
			DoCellJ5();
		}

	}

	COMP_TYPE SingletonStateVariable HumanAgentType ( Idle() ) {

		VALUE Idle() [1, +INF]
		MEETS {

		}

	}

	COMP_TYPE SingletonStateVariable RobotAgentType ( Idle() ) {

		VALUE Idle() [1, +INF]
		MEETS {

		}

	}

	COMPONENT Goal { FLEXIBLE hrc(functional) } : GoalType;
	COMPONENT Mosaic { FLEXIBLE process(functional) } : MosaicProcessType;
	COMPONENT Assembly { FLEXIBLE coordination(functional) } : AssemblyProcessType;
	COMPONENT Robot { FLEXIBLE task(primitive) } : RobotAgentType;
	COMPONENT Human { FLEXIBLE operation(primitive) } : HumanAgentType;


	SYNCHRONIZE Goal.hrc {

		VALUE DoMosaic() {

			r0 Mosaic.process.DoRow1();
			CONTAINS [0, +INF] [0, +INF] r0;

			r1 Mosaic.process.DoRow2();
			CONTAINS [0, +INF] [0, +INF] r1;

			r2 Mosaic.process.DoRow3();
			CONTAINS [0, +INF] [0, +INF] r2;

			r3 Mosaic.process.DoRow4();
			CONTAINS [0, +INF] [0, +INF] r3;

			r4 Mosaic.process.DoRow5();
			CONTAINS [0, +INF] [0, +INF] r4;

		}
	}

	SYNCHRONIZE Mosaic.process {

		VALUE DoRow1() {

			d0 Assembly.coordination.DoCellA1();
			CONTAINS [0, +INF] [0, +INF] d0;

			d1 Assembly.coordination.DoCellB1();
			CONTAINS [0, +INF] [0, +INF] d1;

			d2 Assembly.coordination.DoCellC1();
			CONTAINS [0, +INF] [0, +INF] d2;

			d3 Assembly.coordination.DoCellD1();
			CONTAINS [0, +INF] [0, +INF] d3;

			d4 Assembly.coordination.DoCellE1();
			CONTAINS [0, +INF] [0, +INF] d4;

			d5 Assembly.coordination.DoCellF1();
			CONTAINS [0, +INF] [0, +INF] d5;

			d6 Assembly.coordination.DoCellG1();
			CONTAINS [0, +INF] [0, +INF] d6;

			d7 Assembly.coordination.DoCellH1();
			CONTAINS [0, +INF] [0, +INF] d7;

			d8 Assembly.coordination.DoCellI1();
			CONTAINS [0, +INF] [0, +INF] d8;

			d9 Assembly.coordination.DoCellJ1();
			CONTAINS [0, +INF] [0, +INF] d9;

		}

		VALUE DoRow2() {

			d0 Assembly.coordination.DoCellA2();
			CONTAINS [0, +INF] [0, +INF] d0;

			d1 Assembly.coordination.DoCellB2();
			CONTAINS [0, +INF] [0, +INF] d1;

			d2 Assembly.coordination.DoCellC2();
			CONTAINS [0, +INF] [0, +INF] d2;

			d3 Assembly.coordination.DoCellD2();
			CONTAINS [0, +INF] [0, +INF] d3;

			d4 Assembly.coordination.DoCellE2();
			CONTAINS [0, +INF] [0, +INF] d4;

			d5 Assembly.coordination.DoCellF2();
			CONTAINS [0, +INF] [0, +INF] d5;

			d6 Assembly.coordination.DoCellG2();
			CONTAINS [0, +INF] [0, +INF] d6;

			d7 Assembly.coordination.DoCellH2();
			CONTAINS [0, +INF] [0, +INF] d7;

			d8 Assembly.coordination.DoCellI2();
			CONTAINS [0, +INF] [0, +INF] d8;

			d9 Assembly.coordination.DoCellJ2();
			CONTAINS [0, +INF] [0, +INF] d9;

		}

		VALUE DoRow3() {

			d0 Assembly.coordination.DoCellA3();
			CONTAINS [0, +INF] [0, +INF] d0;

			d1 Assembly.coordination.DoCellB3();
			CONTAINS [0, +INF] [0, +INF] d1;

			d2 Assembly.coordination.DoCellC3();
			CONTAINS [0, +INF] [0, +INF] d2;

			d3 Assembly.coordination.DoCellD3();
			CONTAINS [0, +INF] [0, +INF] d3;

			d4 Assembly.coordination.DoCellE3();
			CONTAINS [0, +INF] [0, +INF] d4;

			d5 Assembly.coordination.DoCellF3();
			CONTAINS [0, +INF] [0, +INF] d5;

			d6 Assembly.coordination.DoCellG3();
			CONTAINS [0, +INF] [0, +INF] d6;

			d7 Assembly.coordination.DoCellH3();
			CONTAINS [0, +INF] [0, +INF] d7;

			d8 Assembly.coordination.DoCellI3();
			CONTAINS [0, +INF] [0, +INF] d8;

			d9 Assembly.coordination.DoCellJ3();
			CONTAINS [0, +INF] [0, +INF] d9;

		}

		VALUE DoRow4() {

			d0 Assembly.coordination.DoCellA4();
			CONTAINS [0, +INF] [0, +INF] d0;

			d1 Assembly.coordination.DoCellB4();
			CONTAINS [0, +INF] [0, +INF] d1;

			d2 Assembly.coordination.DoCellC4();
			CONTAINS [0, +INF] [0, +INF] d2;

			d3 Assembly.coordination.DoCellD4();
			CONTAINS [0, +INF] [0, +INF] d3;

			d4 Assembly.coordination.DoCellE4();
			CONTAINS [0, +INF] [0, +INF] d4;

			d5 Assembly.coordination.DoCellF4();
			CONTAINS [0, +INF] [0, +INF] d5;

			d6 Assembly.coordination.DoCellG4();
			CONTAINS [0, +INF] [0, +INF] d6;

			d7 Assembly.coordination.DoCellH4();
			CONTAINS [0, +INF] [0, +INF] d7;

			d8 Assembly.coordination.DoCellI4();
			CONTAINS [0, +INF] [0, +INF] d8;

			d9 Assembly.coordination.DoCellJ4();
			CONTAINS [0, +INF] [0, +INF] d9;

		}

		VALUE DoRow5() {

			d0 Assembly.coordination.DoCellA5();
			CONTAINS [0, +INF] [0, +INF] d0;

			d1 Assembly.coordination.DoCellB5();
			CONTAINS [0, +INF] [0, +INF] d1;

			d2 Assembly.coordination.DoCellC5();
			CONTAINS [0, +INF] [0, +INF] d2;

			d3 Assembly.coordination.DoCellD5();
			CONTAINS [0, +INF] [0, +INF] d3;

			d4 Assembly.coordination.DoCellE5();
			CONTAINS [0, +INF] [0, +INF] d4;

			d5 Assembly.coordination.DoCellF5();
			CONTAINS [0, +INF] [0, +INF] d5;

			d6 Assembly.coordination.DoCellG5();
			CONTAINS [0, +INF] [0, +INF] d6;

			d7 Assembly.coordination.DoCellH5();
			CONTAINS [0, +INF] [0, +INF] d7;

			d8 Assembly.coordination.DoCellI5();
			CONTAINS [0, +INF] [0, +INF] d8;

			d9 Assembly.coordination.DoCellJ5();
			CONTAINS [0, +INF] [0, +INF] d9;

		}

	}

	SYNCHRONIZE Assembly.coordination {

		VALUE DoCellA1() {

			d0 Robot.task._PickPlace-A1();
			CONTAINS [0, +INF] [0, +INF] d0;

		}

		VALUE DoCellB1() {

			d1 Robot.task._PickPlace-B1();
			CONTAINS [0, +INF] [0, +INF] d1;

		}

		VALUE DoCellC1() {

			d2 Robot.task._PickPlace-C1();
			CONTAINS [0, +INF] [0, +INF] d2;

		}

		VALUE DoCellD1() {

			d3 Human.operation._PickPlace-D1();
			CONTAINS [0, +INF] [0, +INF] d3;

		}

		VALUE DoCellD1() {

			d3 Robot.task._PickPlace-D1();
			CONTAINS [0, +INF] [0, +INF] d3;

		}

		VALUE DoCellE1() {

			d4 Human.operation._PickPlace-E1();
			CONTAINS [0, +INF] [0, +INF] d4;

		}

		VALUE DoCellF1() {

			d5 Human.operation._PickPlace-F1();
			CONTAINS [0, +INF] [0, +INF] d5;

		}

		VALUE DoCellF1() {

			d5 Robot.task._PickPlace-F1();
			CONTAINS [0, +INF] [0, +INF] d5;

		}

		VALUE DoCellG1() {

			d6 Human.operation._PickPlace-G1();
			CONTAINS [0, +INF] [0, +INF] d6;

		}

		VALUE DoCellG1() {

			d6 Robot.task._PickPlace-G1();
			CONTAINS [0, +INF] [0, +INF] d6;

		}

		VALUE DoCellH1() {

			d7 Human.operation._PickPlace-H1();
			CONTAINS [0, +INF] [0, +INF] d7;

		}

		VALUE DoCellH1() {

			d7 Robot.task._PickPlace-H1();
			CONTAINS [0, +INF] [0, +INF] d7;

		}

		VALUE DoCellI1() {

			d8 Human.operation._PickPlace-I1();
			CONTAINS [0, +INF] [0, +INF] d8;

		}

		VALUE DoCellJ1() {

			d9 Human.operation._PickPlace-J1();
			CONTAINS [0, +INF] [0, +INF] d9;

		}

		VALUE DoCellJ1() {

			d9 Robot.task._PickPlace-J1();
			CONTAINS [0, +INF] [0, +INF] d9;

		}

		VALUE DoCellA2() {

			d0 Robot.task._PickPlace-A2();
			CONTAINS [0, +INF] [0, +INF] d0;

		}

		VALUE DoCellB2() {

			d1 Human.operation._PickPlace-B2();
			CONTAINS [0, +INF] [0, +INF] d1;

		}

		VALUE DoCellB2() {

			d1 Robot.task._PickPlace-B2();
			CONTAINS [0, +INF] [0, +INF] d1;

		}

		VALUE DoCellC2() {

			d2 Human.operation._PickPlace-C2();
			CONTAINS [0, +INF] [0, +INF] d2;

		}

		VALUE DoCellC2() {

			d2 Robot.task._PickPlace-C2();
			CONTAINS [0, +INF] [0, +INF] d2;

		}

		VALUE DoCellD2() {

			d3 Human.operation._PickPlace-D2();
			CONTAINS [0, +INF] [0, +INF] d3;

		}

		VALUE DoCellD2() {

			d3 Robot.task._PickPlace-D2();
			CONTAINS [0, +INF] [0, +INF] d3;

		}

		VALUE DoCellE2() {

			d4 Human.operation._PickPlace-E2();
			CONTAINS [0, +INF] [0, +INF] d4;

		}

		VALUE DoCellF2() {

			d5 Human.operation._PickPlace-F2();
			CONTAINS [0, +INF] [0, +INF] d5;

		}

		VALUE DoCellF2() {

			d5 Robot.task._PickPlace-F2();
			CONTAINS [0, +INF] [0, +INF] d5;

		}

		VALUE DoCellG2() {

			d6 Human.operation._PickPlace-G2();
			CONTAINS [0, +INF] [0, +INF] d6;

		}

		VALUE DoCellH2() {

			d7 Human.operation._PickPlace-H2();
			CONTAINS [0, +INF] [0, +INF] d7;

		}

		VALUE DoCellH2() {

			d7 Robot.task._PickPlace-H2();
			CONTAINS [0, +INF] [0, +INF] d7;

		}

		VALUE DoCellI2() {

			d8 Human.operation._PickPlace-I2();
			CONTAINS [0, +INF] [0, +INF] d8;

		}

		VALUE DoCellJ2() {

			d9 Human.operation._PickPlace-J2();
			CONTAINS [0, +INF] [0, +INF] d9;

		}

		VALUE DoCellJ2() {

			d9 Robot.task._PickPlace-J2();
			CONTAINS [0, +INF] [0, +INF] d9;

		}

		VALUE DoCellA3() {

			d0 Robot.task._PickPlace-A3();
			CONTAINS [0, +INF] [0, +INF] d0;

		}

		VALUE DoCellB3() {

			d1 Robot.task._PickPlace-B3();
			CONTAINS [0, +INF] [0, +INF] d1;

		}

		VALUE DoCellC3() {

			d2 Robot.task._PickPlace-C3();
			CONTAINS [0, +INF] [0, +INF] d2;

		}

		VALUE DoCellD3() {

			d3 Human.operation._PickPlace-D3();
			CONTAINS [0, +INF] [0, +INF] d3;

		}

		VALUE DoCellD3() {

			d3 Robot.task._PickPlace-D3();
			CONTAINS [0, +INF] [0, +INF] d3;

		}

		VALUE DoCellE3() {

			d4 Human.operation._PickPlace-E3();
			CONTAINS [0, +INF] [0, +INF] d4;

		}

		VALUE DoCellF3() {

			d5 Human.operation._PickPlace-F3();
			CONTAINS [0, +INF] [0, +INF] d5;

		}

		VALUE DoCellF3() {

			d5 Robot.task._PickPlace-F3();
			CONTAINS [0, +INF] [0, +INF] d5;

		}

		VALUE DoCellG3() {

			d6 Human.operation._PickPlace-G3();
			CONTAINS [0, +INF] [0, +INF] d6;

		}

		VALUE DoCellH3() {

			d7 Human.operation._PickPlace-H3();
			CONTAINS [0, +INF] [0, +INF] d7;

		}

		VALUE DoCellH3() {

			d7 Robot.task._PickPlace-H3();
			CONTAINS [0, +INF] [0, +INF] d7;

		}

		VALUE DoCellI3() {

			d8 Human.operation._PickPlace-I3();
			CONTAINS [0, +INF] [0, +INF] d8;

		}

		VALUE DoCellJ3() {

			d9 Human.operation._PickPlace-J3();
			CONTAINS [0, +INF] [0, +INF] d9;

		}

		VALUE DoCellJ3() {

			d9 Robot.task._PickPlace-J3();
			CONTAINS [0, +INF] [0, +INF] d9;

		}

		VALUE DoCellA4() {

			d0 Human.operation._PickPlace-A4();
			CONTAINS [0, +INF] [0, +INF] d0;

		}

		VALUE DoCellA4() {

			d0 Robot.task._PickPlace-A4();
			CONTAINS [0, +INF] [0, +INF] d0;

		}

		VALUE DoCellB4() {

			d1 Human.operation._PickPlace-B4();
			CONTAINS [0, +INF] [0, +INF] d1;

		}

		VALUE DoCellB4() {

			d1 Robot.task._PickPlace-B4();
			CONTAINS [0, +INF] [0, +INF] d1;

		}

		VALUE DoCellC4() {

			d2 Robot.task._PickPlace-C4();
			CONTAINS [0, +INF] [0, +INF] d2;

		}

		VALUE DoCellD4() {

			d3 Human.operation._PickPlace-D4();
			CONTAINS [0, +INF] [0, +INF] d3;

		}

		VALUE DoCellD4() {

			d3 Robot.task._PickPlace-D4();
			CONTAINS [0, +INF] [0, +INF] d3;

		}

		VALUE DoCellE4() {

			d4 Human.operation._PickPlace-E4();
			CONTAINS [0, +INF] [0, +INF] d4;

		}

		VALUE DoCellE4() {

			d4 Robot.task._PickPlace-E4();
			CONTAINS [0, +INF] [0, +INF] d4;

		}

		VALUE DoCellF4() {

			d5 Human.operation._PickPlace-F4();
			CONTAINS [0, +INF] [0, +INF] d5;

		}

		VALUE DoCellG4() {

			d6 Human.operation._PickPlace-G4();
			CONTAINS [0, +INF] [0, +INF] d6;

		}

		VALUE DoCellG4() {

			d6 Robot.task._PickPlace-G4();
			CONTAINS [0, +INF] [0, +INF] d6;

		}

		VALUE DoCellH4() {

			d7 Human.operation._PickPlace-H4();
			CONTAINS [0, +INF] [0, +INF] d7;

		}

		VALUE DoCellI4() {

			d8 Human.operation._PickPlace-I4();
			CONTAINS [0, +INF] [0, +INF] d8;

		}

		VALUE DoCellI4() {

			d8 Robot.task._PickPlace-I4();
			CONTAINS [0, +INF] [0, +INF] d8;

		}

		VALUE DoCellJ4() {

			d9 Human.operation._PickPlace-J4();
			CONTAINS [0, +INF] [0, +INF] d9;

		}

		VALUE DoCellJ4() {

			d9 Robot.task._PickPlace-J4();
			CONTAINS [0, +INF] [0, +INF] d9;

		}

		VALUE DoCellA5() {

			d0 Robot.task._PickPlace-A5();
			CONTAINS [0, +INF] [0, +INF] d0;

		}

		VALUE DoCellB5() {

			d1 Robot.task._PickPlace-B5();
			CONTAINS [0, +INF] [0, +INF] d1;

		}

		VALUE DoCellC5() {

			d2 Robot.task._PickPlace-C5();
			CONTAINS [0, +INF] [0, +INF] d2;

		}

		VALUE DoCellD5() {

			d3 Human.operation._PickPlace-D5();
			CONTAINS [0, +INF] [0, +INF] d3;

		}

		VALUE DoCellD5() {

			d3 Robot.task._PickPlace-D5();
			CONTAINS [0, +INF] [0, +INF] d3;

		}

		VALUE DoCellE5() {

			d4 Human.operation._PickPlace-E5();
			CONTAINS [0, +INF] [0, +INF] d4;

		}

		VALUE DoCellE5() {

			d4 Robot.task._PickPlace-E5();
			CONTAINS [0, +INF] [0, +INF] d4;

		}

		VALUE DoCellF5() {

			d5 Human.operation._PickPlace-F5();
			CONTAINS [0, +INF] [0, +INF] d5;

		}

		VALUE DoCellG5() {

			d6 Human.operation._PickPlace-G5();
			CONTAINS [0, +INF] [0, +INF] d6;

		}

		VALUE DoCellG5() {

			d6 Robot.task._PickPlace-G5();
			CONTAINS [0, +INF] [0, +INF] d6;

		}

		VALUE DoCellH5() {

			d7 Human.operation._PickPlace-H5();
			CONTAINS [0, +INF] [0, +INF] d7;

		}

		VALUE DoCellI5() {

			d8 Human.operation._PickPlace-I5();
			CONTAINS [0, +INF] [0, +INF] d8;

		}

		VALUE DoCellI5() {

			d8 Robot.task._PickPlace-I5();
			CONTAINS [0, +INF] [0, +INF] d8;

		}

		VALUE DoCellJ5() {

			d9 Human.operation._PickPlace-J5();
			CONTAINS [0, +INF] [0, +INF] d9;

		}

		VALUE DoCellJ5() {

			d9 Robot.task._PickPlace-J5();
			CONTAINS [0, +INF] [0, +INF] d9;

		}

	}


}

