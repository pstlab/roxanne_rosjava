DOMAIN ACTING_TEST_DDL
{
	TEMPORAL_MODULE temporal_module = [0, 100], 100;
	
	COMP_TYPE SingletonStateVariable ActingGoalType (Idle(), DoSomething())
	{
		VALUE Idle() [1, +INF]
		MEETS {
			DoSomething();
		}
		
		VALUE DoSomething() [1, +INF]
		MEETS {
			Idle();
		}
	}
	
	COMP_TYPE SingletonStateVariable ActingBehaviorType (Idle(), DoA(), DoB(), DoC())
	{
		VALUE Idle() [1, +INF]
		MEETS {
			DoA();
			DoB();
			DoC();
		}
		
		VALUE DoA() [3, 10]
		MEETS {
			Idle();
		}
		
		VALUE DoB() [8, 10]
        MEETS {
            Idle();
        }

        VALUE DoC() [5, 10]
        MEETS {
            Idle();
        }
	}

	COMPONENT Goal {FLEXIBLE goals(functional)} : ActingGoalType;
	COMPONENT Acting {FLEXIBLE tasks(primitive)} : ActingBehaviorType;
		
	SYNCHRONIZE Goal.goals
	{
		VALUE DoSomething()
		{
			cd0 Acting.tasks.DoA();
			cd1 Acting.tasks.DoC();

			CONTAINS [0, +INF] [0, +INF] cd0;
			CONTAINS [0, +INF] [0, +INF] cd1;
		}


        VALUE DoSomething()
        {
            cd0 Acting.tasks.DoB();
            cd1 Acting.tasks.DoC();

            CONTAINS [0, +INF] [0, +INF] cd0;
            CONTAINS [0, +INF] [0, +INF] cd1;

            cd0 BEFORE [0, +INF] cd1;
        }

        VALUE DoSomething()
        {
            cd0 Acting.tasks.DoA();
            cd1 Acting.tasks.DoB();
            cd2 Acting.tasks.DoC();

            CONTAINS [0, +INF] [0, +INF] cd0;
            CONTAINS [0, +INF] [0, +INF] cd1;
            CONTAINS [0, +INF] [0, +INF] cd2;

            cd0 BEFORE [0, +INF] cd2;
            cd1 BEFORE [0, +INF] cd2;
        }
	}
}