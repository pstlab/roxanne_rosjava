DOMAIN HOSPITAL_IROS23_MAP1
{
	TEMPORAL_MODULE temporal_module = [0, 1000], 1000;
	
	PAR_TYPE EnumerationParameterType location = {
		home, 
		farmacy, farmacy_hri, farmacy_user, 
		room1, room1_hri, room1_user,
		room2, room2_hri, room2_user,
		room3, room3_hri, room3_user
	};
	
	PAR_TYPE EnumerationParameterType user = {
		fragile, average, reliable
	};
	
	PAR_TYPE EnumerationParameterType task = {
		technical, interaction, social 
	};
	
	COMP_TYPE SingletonStateVariable RobotBaseType(
		At(location), _MoveTo(location, task, user)) {
		
		VALUE At(?source) [1, +INF]
		MEETS {
			_MoveTo(?destination, ?task, ?user);
		}
		
		VALUE _MoveTo(?goal, ?task, ?user) [1, 50]
		MEETS {
			At(?location);
			?location = ?goal;
		}
	}
	
	COMP_TYPE SingletonStateVariable RobotMotionControllerType(
		Still(location), 
		NavigateTo(location, task, user), 
		Enter(location, task, user), 
		Leave(location, task, user), 
		Inside(location), 
		Approach(location, location, task, user), 
		Deliver(location, location, task, user), 
		Monitor(location, location, task, user)) {
		
		
		VALUE Still(?location) [1, +INF] 
		MEETS {
		
			NavigateTo(?room, ?task, ?user);
			Enter(?room, ?task, ?user);
		}
		
		VALUE NavigateTo(?room, ?task, ?user) [1, +INF]
		MEETS {
			
			Still(?destination);
			?destination = ?room;
		}
		
		VALUE Enter(?hri, ?task, ?user) [1, +INF]
		MEETS {
			
			Inside(?location);
			?location = ?hri;
		}
		
		VALUE Inside(?hri) [1, +INF]
		MEETS {
			
			Approach(?hri, ?user_location, ?task, ?user);
			Deliver(?hri, ?user_location, ?task, ?user);
			Monitor(?hri, ?user_location, ?task, ?user);
			Leave(?room, ?task, ?user);
		}
		
		VALUE Leave(?room, ?task, ?user) [1, +INF]
		MEETS {
		
			Still(?location);
			?location = ?room;
		}
		
		VALUE Approach(?hri, ?user_location, ?task, ?user) [1, +INF]
		MEETS {
			
			Inside(?location);
			?location = ?hri;
		}
		
		VALUE Deliver(?hri, ?user_location, ?task, ?user) [1, +INF]
		MEETS {
			
			Inside(?location);
			?location = ?hri;
		}
		
		VALUE Monitor(?hri, ?user_location, ?task, ?user) [1, +INF]
		MEETS {
			
			Inside(?location);
			?location = ?hri;
		}
	}
	
	
	COMP_TYPE SingletonStateVariable RobotSkillType(
		Idle(), 
		PickDrug(location, location, location),
		DeliverDrug(location, location, location),
		HelpPatient(location, location, location),
		MonitorPatient(location, location, location),
		GoHome()) {
		
		VALUE Idle() [1, +INF]
		MEETS {
			PickDrug(?room, ?hri, ?user);
			DeliverDrug(?room, ?hri, ?user);
			HelpPatient(?room, ?hri, ?user);
			MonitorPatient(?room, ?hri, ?user);
			GoHome();
		}
		
		VALUE PickDrug(?room, ?hri, ?user) [1, +INF]
		MEETS {
			Idle();
		}
		
		VALUE DeliverDrug(?room, ?hri, ?user) [1, +INF]
		MEETS {
			Idle();
		}
		
		VALUE HelpPatient(?room, ?hri, ?user) [1, +INF]
		MEETS {
			Idle();
		}
		
		VALUE MonitorPatient(?room, ?hri, ?user) [1, +INF]
		MEETS {
			Idle();
		}
		
		VALUE GoHome() [1, +INF]
		MEETS {
			Idle();
		} 	
	}


	COMP_TYPE SingletonStateVariable RobotServiceType(
		Idle(), 
		DeliverDrug(location, location, location), 
		Patroling(location, location, location),
		Emergency(location, location, location))  {
		
		VALUE Idle() [1, +INF] 
		MEETS {
		
			DeliverDrug(?room, ?room_hri, ?room_user);
			Patroling(?room, ?room_hri, ?room_user);
			Emergency(?room, ?room_hri, ?room_user);
		}
		
		VALUE DeliverDrug(?room, ?hri, ?user) [1, +INF] 
		MEETS {
			Idle();
		}
		
		VALUE Patroling(?room, ?hri, ?user) [1, +INF] 
		MEETS {
			Idle();
		}
		
		VALUE Emergency(?room, ?hri, ?user) [1, +INF] 
		MEETS {
			Idle();
		}
	}


	COMPONENT RobotBase {FLEXIBLE positions(primitive)} : RobotBaseType;
	COMPONENT RobotMotionController {FLEXIBLE motions(primitive)} : RobotMotionControllerType;
	COMPONENT RobotSkill {FLEXIBLE actions(functional)} : RobotSkillType;
	COMPONENT RobotService {FLEXIBLE goals(functional)} : RobotServiceType;

	SYNCHRONIZE RobotService.goals {
	
		VALUE DeliverDrug(?room, ?hri, ?user) {
		
			d0 <!> RobotSkill.actions.PickDrug(?room0, ?hri0, ?user0);
			d1 <!> RobotSkill.actions.DeliverDrug(?room1, ?hri1, ?user1);
			d2 <!> RobotSkill.actions.GoHome();
			
			CONTAINS [0, +INF] [0, +INF] d0;
			CONTAINS [0, +INF] [0, +INF] d1;
						
			d0 BEFORE [0, +INF] d1;
			d1 BEFORE [0, +INF] d2;
			
			?room0 = farmacy; 
			?hri0 = farmacy_hri; 
			?user0 = farmacy_user;
			
			?room1 = ?room;
			?hri1 = ?hri;
			?user1 = ?user; 
		}

		VALUE Patroling(?room, ?hri, ?user) {

            d0 <!> RobotSkill.actions.MonitorPatient(?room0, ?hri0, ?user0);
            d1 <!> RobotSkill.actions.GoHome();

            CONTAINS [0, +INF] [0, +INF] d0;
            CONTAINS [0, +INF] [0, +INF] d1;

            d0 BEFORE [0, +INF] d1;

            ?room0 = ?room;
            ?hri0 = ?hri;
            ?user0 = ?user;
        }

        VALUE Emergency(?room, ?hri, ?user) {

            d0 <!> RobotSkill.actions.HelpPatient(?room0, ?hri0, ?user0);
            d1 <!> RobotSkill.actions.GoHome();

            CONTAINS [0, +INF] [0, +INF] d0;
            CONTAINS [0, +INF] [0, +INF] d1;

            d0 BEFORE [0, +INF] d1;

            ?room0 = ?room;
            ?hri0 = ?hri;
            ?user0 = ?user;
        }
	}


	SYNCHRONIZE RobotSkill.actions {

	    VALUE MonitorPatient(?room, ?hri, ?user) {

	        d0 <!> RobotMotionController.motions.NavigateTo(?room0, ?task0, ?user0);
	        d1 <!> RobotMotionController.motions.Enter(?hri1, ?task1, ?user1);
	        d2 <!> RobotMotionController.motions.Monitor(?hri2, ?hri_user2, ?task2, ?user2);
	        d3 <!> RobotMotionController.motions.Leave(?room3, ?task3, ?user3);

	        CONTAINS [0, +INF] [0, +INF] d0;
	        CONTAINS [0, +INF] [0, +INF] d1;
	        CONTAINS [0, +INF] [0, +INF] d2;
	        CONTAINS [0, +INF] [0, +INF] d3;

	        d0 BEFORE [0, +INF] d1;
	        d1 BEFORE [0, +INF] d2;
	        d2 BEFORE [0, +INF] d3;

	        ?room0 = ?room;
	        ?task0 = social;
	        ?user0 = average;

	        ?hri1 = ?hri;
	        ?task1 = social;
	        ?user1 = fragile;

	        ?hri2 = ?hri;
	        ?hri_user2 = ?user;
	        ?task2 = interaction;
	        ?user2 = fragile;

	        ?room3 = ?room;
            ?task3 = social;
            ?user3 = fragile;
	    }

	    VALUE HelpPatient(?room, ?hri, ?user) {

            d0 <!> RobotMotionController.motions.NavigateTo(?room0, ?task0, ?user0);
            d1 <!> RobotMotionController.motions.Enter(?hri1, ?task1, ?user1);
            d2 <!> RobotMotionController.motions.Approach(?hri2, ?hri_user2, ?task2, ?user2);
            d3 <!> RobotMotionController.motions.Leave(?room3, ?task3, ?user3);

            CONTAINS [0, +INF] [0, +INF] d0;
            CONTAINS [0, +INF] [0, +INF] d1;
            CONTAINS [0, +INF] [0, +INF] d2;
            CONTAINS [0, +INF] [0, +INF] d3;

            d0 BEFORE [0, +INF] d1;
            d1 BEFORE [0, +INF] d2;
            d2 BEFORE [0, +INF] d3;

            ?room0 = ?room;
            ?task0 = technical;
            ?user0 = reliable;

            ?hri1 = ?hri;
            ?task1 = interaction;
            ?user1 = average;

            ?hri2 = ?hri;
            ?hri_user2 = ?user;
            ?task2 = interaction;
            ?user2 = fragile;

            ?room3 = ?room;
            ?task3 = interaction;
            ?user3 = fragile;
        }
	
		VALUE PickDrug(?room, ?hri, ?user) {
		
			d0 <!> RobotMotionController.motions.NavigateTo(?room0, ?task0, ?user0);
			d1 <!> RobotMotionController.motions.Enter(?hri1, ?task1, ?user1);
			d2 <!> RobotMotionController.motions.Approach(?hri2, ?hri_user2, ?task2, ?user2);
			d3 <!> RobotMotionController.motions.Leave(?room3, ?task3, ?user3);
			
			
			CONTAINS [0, +INF] [0, +INF] d0;
			CONTAINS [0, +INF] [0, +INF] d1;
			CONTAINS [0, +INF] [0, +INF] d2;
			CONTAINS [0, +INF] [0, +INF] d3;
			
			d0 BEFORE [0, +INF] d1;
			d1 BEFORE [0, +INF] d2;
			d2 BEFORE [0, +INF] d3;
			
			?room0 = ?room;
			?task0 = social;
			?user0 = average;
			
			?hri1 = ?hri;
			?task1 = technical;
			?user1 = reliable;
			
			?hri2 = ?hri;
			?hri_user2 = ?user;
			?task2 = interaction;
			?user2 = reliable;
			
			?room3 = ?room; 
			?task3 = technical;
			?user3 = reliable;
		}	
		
		VALUE DeliverDrug(?room, ?hri, ?user) {
		
			d0 <!> RobotMotionController.motions.NavigateTo(?room0, ?task0, ?user0);
			d1 <!> RobotMotionController.motions.Enter(?hri1, ?task1, ?user1);
			d2 <!> RobotMotionController.motions.Deliver(?hri2, ?hri_user2, ?task2, ?user2);
			d3 <!> RobotMotionController.motions.Leave(?room3, ?task3, ?user3);
			
			
			CONTAINS [0, +INF] [0, +INF] d0;
			CONTAINS [0, +INF] [0, +INF] d1;
			CONTAINS [0, +INF] [0, +INF] d2;
			
			d0 BEFORE [0, +INF] d1;
			d1 BEFORE [0, +INF] d2;
			d2 BEFORE [0, +INF] d3;
			
			?room0 = ?room;
			?task0 = social;
			?user0 = average;
			
			?hri1 = ?hri;
			?task1 = social;
			?user1 = fragile;
			
			?hri2 = ?hri;
			?hri_user2 = ?user;
			?task2 = interaction;
			?user2 = fragile;
			
			?room3 = ?room; 
			?task3 = social;
			?user3 = fragile;
		}
		
		VALUE GoHome() {
		
			d0 <!> RobotMotionController.motions.NavigateTo(?room0, ?task0, ?user0);
			
			CONTAINS [0, +INF] [0, +INF] d0;
			
			?room0 = home;
			?task0 = social;
			?user0 = average;
		}
	}
	
	
	SYNCHRONIZE RobotMotionController.motions {
	
		VALUE Enter(?location, ?task, ?user) {
		
			d0 <!> RobotBase.positions._MoveTo(?location0, ?task0, ?user0);
			
			CONTAINS [0, +INF] [0, +INF] d0;
			
			?location0 = ?location;
			?task0 = ?task;
			?user0 = ?user;
		}
		
		VALUE Approach(?hri, ?user_location, ?task, ?user) {
		
			d0 <!> RobotBase.positions._MoveTo(?location0, ?task0, ?user0);
			d1 <!> RobotBase.positions._MoveTo(?location1, ?task1, ?user1);
			
			CONTAINS [0, +INF] [0, +INF] d0;
			CONTAINS [0, +INF] [0, +INF] d1;
			
			d0 BEFORE [5, +INF] d1;
			
			?location0 = ?user_location;
			?task0 = ?task;
			?user0 = ?user;
			
			?location1 = ?hri;
			?task1 = ?task;
			?user1 = ?user;
		}
		
		VALUE Monitor(?hri, ?user_location, ?task, ?user) {
		
			d0 <!> RobotBase.positions._MoveTo(?location0, ?task0, ?user0);
			d1 <!> RobotBase.positions._MoveTo(?location1, ?task1, ?user1);
			
			CONTAINS [0, +INF] [0, +INF] d0;
			CONTAINS [0, +INF] [0, +INF] d1;
			
			d0 BEFORE [5, +INF] d1;
			
			?location0 = ?user_location;
			?task0 = ?task;
			?user0 = ?user;
			
			?location1 = ?hri;
			?task1 = ?task;
			?user1 = ?user;
		}
		
		VALUE Deliver(?hri, ?user_location, ?task, ?user) {
		
			d0 <!> RobotBase.positions._MoveTo(?location0, ?task0, ?user0);
			d1 <!> RobotBase.positions._MoveTo(?location1, ?task1, ?user1);
			
			CONTAINS [0, +INF] [0, +INF] d0;
			CONTAINS [0, +INF] [0, +INF] d1;
			
			d0 BEFORE [5, +INF] d1;
			
			?location0 = ?user_location;
			?task0 = ?task;
			?user0 = ?user;
			
			?location1 = ?hri;
			?task1 = ?task;
			?user1 = ?user;
		}
		
		VALUE Leave(?room, ?task, ?user) {
		
			d0 <!> RobotBase.positions._MoveTo(?location0, ?task0, ?user0);
			
			CONTAINS [0, +INF] [0, +INF] d0;
			
			?location0 = ?room;
			?task0 = ?task;
			?user0 = ?user;
		}


		VALUE NavigateTo(?location, ?task, ?user) {

		    d0 <!> RobotBase.positions._MoveTo(?l0, ?t0, ?u0);

		    CONTAINS [0, +INF] [0, +INF] d0;

		    ?l0 = ?location;
		    ?t0 = ?task;
		    ?u0 = ?user;
		}
	}
}
