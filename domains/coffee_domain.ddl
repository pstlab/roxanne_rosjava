DOMAIN COFFEE_DOMAIN_DDL
{
	TEMPORAL_MODULE temporal_module = [0, 1000], 1000;
	
	PAR_TYPE EnumerationParameterType obstacle = {  table_1 , table_2 , table_3 , table_4, desk_1, desk_2};
	PAR_TYPE EnumerationParameterType location = {  table_1_nord , table_1_sud , table_1_est , table_1_ovest , table_2_nord , table_2_sud , table_2_est , table_2_ovest , table_3_nord , table_3_sud , table_3_est , table_3_ovest , table_4_nord , table_4_sud , desk_1_nord , desk_1_sud , desk_1_est , desk_1_ovest, desk_2_nord , desk_2_sud , desk_2_est , desk_2_ovest};
	PAR_TYPE EnumerationParameterType object = { lattina_1 , lattina_2, lattina_3};
	PAR_TYPE EnumerationParameterType pose = {  table_1_nord , table_1_sud , table_1_est , table_1_ovest , table_2_nord , table_2_sud , table_2_est , table_2_ovest , table_3_nord , table_3_sud , table_3_est , table_3_ovest , table_4_nord , table_4_sud , desk_1_nord , desk_1_sud , desk_1_est , desk_1_ovest,  desk_2_nord , desk_2_sud , desk_2_est , desk_2_ovest};
	PAR_TYPE EnumerationParameterType sentence = {welcome, here_is_your_order, goodbye, picking_up_item, placing_item};




	// Definizione del tipo della componente base_controller (spostamento del robot)
	
	COMP_TYPE SingletonStateVariable Base_ControllerType (
		At(location), _Moving_To(location)) 
	{
		VALUE At(?l) [1, +INF]
		MEETS {
			_Moving_To(?d);	//Gli elementi che cominciano con _ sono non controllabili
		}

		VALUE _Moving_To(?l) [1, 10]
		MEETS {
			At(?d);
			?d = ?l;
			//per indicare che la posizione iniziale del moving_to deve essere uguale a quella dell at
		}
	}

		COMP_TYPE SingletonStateVariable RobotType(
			Idle(), GetObject(location, object, pose), PutObject(location, object, pose)) //la prima location e' quella iniziale, la seconda e' quella del tavolo da servire
			{
				VALUE Idle() [1, +INF]
				MEETS {
					GetObject(?location_init, ?obj, ?pose_init);
					PutObject(?location_fin, ?obj_fin, ?pose_fin);
				}

				VALUE GetObject(?location_init, ?obj, ?pose_init) [1, +INF] //Per ora termine massimo +INF
				MEETS {
					Idle();
				}

				VALUE PutObject(?location_fin, ?obj_fin, ?pose_fin) [1, +INF] //Per ora termine massimo +INF
				MEETS {
					Idle();
				}
			}

		COMP_TYPE SingletonStateVariable Voice_ControllerType (
			_Saying(sentence), Idle()) {

			VALUE Idle() [1, +INF]
			MEETS {
				_Saying(?s);
			}

			VALUE _Saying(?s) [1, 30]
			MEETS {
				Idle();
			}
		}

		//Definizione del tipo della componente goal (servizio ai tavoli).
		//Questo componente non ha corrispondenza nel modello del robot, serve per
		//gestire le richieste di servizio ai tavoli sincronizzando le varie componenti.
			COMP_TYPE SingletonStateVariable GoalType(
				Idle(), Serve(location, object, pose, location, pose), Clear(location, object, pose, location, pose)) //la prima location e' quella iniziale, la seconda e' quella del tavolo da servire
				{
					VALUE Idle() [1, +INF]
					MEETS {
						Serve(?location_init, ?obj, ?pose_init, ?location_fin, ?pose_fin);
						Clear(?location_init, ?obj, ?pose_init, ?location_fin, ?pose_fin);
					}

					VALUE Serve(?loc_init, ?obj, ?pose, ?loc_fin, ?pose_fin) [1, +INF] //Per ora termine massimo +INF
					MEETS {
						Idle();
					}

					VALUE Clear(?loc_init, ?obj, ?pose, ?loc_fin, ?pose_fin) [1, +INF] //Per ora termine massimo +INF
					MEETS {
						Idle();
					}
				}

			
	//Definizione delle componenti del robot
	COMPONENT RobotBase {FLEXIBLE positions(primitive)} : Base_ControllerType;
	COMPONENT RobotVoice {FLEXIBLE utterrances(primitive)} : Voice_ControllerType;
	COMPONENT Robot {FLEXIBLE skills(primitive)} : RobotType;
	COMPONENT Service {FLEXIBLE goals(functional)} : GoalType;


//Regole per la sincronizzazione del serving e clearing
SYNCHRONIZE Robot.skills {

 //Atto di andare in un posto e prendere l-oggetto richiesto
 VALUE GetObject(?location_init, ?obj, ?pose_init){	//Predicato

		 cd0 RobotBase.positions.At(?loc0);	//Vado al tavolo di rifornimento
		 cd1 RobotVoice.utterrances._Saying(?sentence);	//Prendo oggetto


		 DURING [0, +INF] [0, +INF] cd0;
		 CONTAINS [0, +INF] [0, +INF] cd1;

		 ?loc0 = ?location_init;
		 ?sentence = picking_up_item;
	 }

  //Atto di andare in un posto e poggiare l-oggetto richiesto
	 VALUE PutObject(?location_fin, ?obj_fin, ?pose_fin){	//Predicato

			 cd0 RobotBase.positions.At(?loc0);	//Vado al tavolo di rifornimento
			 cd1 RobotVoice.utterrances._Saying(?sentence);	//Prendo oggetto


			 DURING [0, +INF] [0, +INF] cd0;
			 CONTAINS [0, +INF] [0, +INF] cd1;

			 ?loc0 = ?location_fin;
			 ?sentence = placing_item;
		 }
}


//Regole per la sincronizzazione del serving e clearing
SYNCHRONIZE Service.goals {

 VALUE Serve(?location_init, ?obj, ?pose_init, ?location_fin, ?pose_fin){	//Predicato

		 cd0 Robot.skills.GetObject(?loc_get, ?obj_get, ?pos_get);
		 cd1 Robot.skills.PutObject(?loc_put, ?obj_put, ?pos_put);
	 	 cd2 RobotBase.positions.At(?home);

		 CONTAINS [0, +INF] [0, +INF] cd0;
		 CONTAINS [0, +INF] [0, +INF] cd1;
		 cd0 BEFORE [0, +INF] cd1;
		 cd1 BEFORE [0, +INF] cd2;
		 CONTAINS [0, +INF] [0, +INF] cd2;

		 ?loc_get = ?location_init;
		 ?obj_get = ?obj;
		 ?pos_get = ?pose_init;
		 ?loc_put = ?location_fin;
		 ?obj_put = ?obj;
		 ?pos_put = ?pose_fin;

		 ?home = desk_2_sud;												//TODO:MODIFICA POSIZIONI E HOME
	 }

	VALUE Clear(?location_init, ?obj, ?pose_init, ?location_fin, ?pose_fin){	//Predicato

  		 cd0 Robot.skills.GetObject(?loc_get, ?obj_get, ?pos_get);
  		 cd1 Robot.skills.PutObject(?loc_put, ?obj_put, ?pos_put);
  	 	 cd2 RobotBase.positions.At(?home);


  		 CONTAINS [0, +INF] [0, +INF] cd0;
  		 CONTAINS [0, +INF] [0, +INF] cd1;
  		 cd0 BEFORE [0, +INF] cd1;
  		 cd1 BEFORE [0, +INF] cd2;
  		 CONTAINS [0, +INF] [0, +INF] cd2;

  		 ?loc_get = ?location_init;
  		 ?obj_get = ?obj;
  		 ?pos_get = ?pose_init;
  		 ?loc_put = ?location_fin;
  		 ?obj_put = ?obj;
  		 ?pos_put = ?pose_fin;

  		 ?home = desk_2_sud;												//TODO:MODIFICA POSIZIONI E HOME
  	 }
	}
}
