package at.tugraz.beislrallye;

import junit.framework.TestCase;

import org.json.JSONException;

/**
 * Created by Matthias on 07.05.2015.
 */
public class DirectionsApiJSONParserTest extends TestCase {
    public void testDirectionsApiJSONParser() {
        DirectionsApiJSONParser parser = new DirectionsApiJSONParser(json);
        try {
            String resultPoints = parser.getPoints();
            assertEquals(points, resultPoints);
        } catch (JSONException e) {
            assert(false);
        }

    }

    private String points = "cbf~Gwlj}Af@Q`@[^dAhEfOfAfEDNh@`@pAdAZh@^v@Nh@sCdCm@p@}@d@cC|@{B`AkBp@aCz@aATsBVcCRaDN_@@m@Jy@ZWPmB|@iA^{@^WZMXaBbIYv@yB|BkD`DeBhAc@j@]`ASnDc@hFOnBk@|AEN@\\LZDBAH?TDTh@jBNHH?LCHb@D^Bf@H~@h@fLNxBSLwCbBeI~DuBdBAZATCbAC~AEzBcBAy@Dx@EbB@D{BB_BDyA@[tBeBdI_EvCcBRMOyBe@qJCu@I_AIgAIc@MBI?OIi@kB";

    private String json = "{\n" +
            "   \"routes\" : [\n" +
            "      {\n" +
            "         \"bounds\" : {\n" +
            "            \"northeast\" : {\n" +
            "               \"lat\" : 47.0703549,\n" +
            "               \"lng\" : 15.45971\n" +
            "            },\n" +
            "            \"southwest\" : {\n" +
            "               \"lat\" : 47.05549000000001,\n" +
            "               \"lng\" : 15.434643\n" +
            "            }\n" +
            "         },\n" +
            "         \"copyrights\" : \"Map data Â©2015 Google\",\n" +
            "         \"legs\" : [\n" +
            "            {\n" +
            "               \"distance\" : {\n" +
            "                  \"text\" : \"3.3 km\",\n" +
            "                  \"value\" : 3252\n" +
            "               },\n" +
            "               \"duration\" : {\n" +
            "                  \"text\" : \"40 mins\",\n" +
            "                  \"value\" : 2385\n" +
            "               },\n" +
            "               \"end_address\" : \"Grieskai 6, 8020 Graz, Austria\",\n" +
            "               \"end_location\" : {\n" +
            "                  \"lat\" : 47.0703549,\n" +
            "                  \"lng\" : 15.434643\n" +
            "               },\n" +
            "               \"start_address\" : \"Graz University of Technology, Inffeldgasse 18, 8010 Graz, Austria\",\n" +
            "               \"start_location\" : {\n" +
            "                  \"lat\" : 47.0584155,\n" +
            "                  \"lng\" : 15.4594769\n" +
            "               },\n" +
            "               \"steps\" : [\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"45 m\",\n" +
            "                        \"value\" : 45\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"1 min\",\n" +
            "                        \"value\" : 33\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 47.05805,\n" +
            "                        \"lng\" : 15.45971\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Head \\u003cb\\u003esouth\\u003c/b\\u003e toward \\u003cb\\u003eInffeldgasse\\u003c/b\\u003e\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"cbf~Gwlj}APETK`@[\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 47.0584155,\n" +
            "                        \"lng\" : 15.4594769\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"WALKING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"0.3 km\",\n" +
            "                        \"value\" : 345\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"4 mins\",\n" +
            "                        \"value\" : 249\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 47.056517,\n" +
            "                        \"lng\" : 15.4557585\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Turn \\u003cb\\u003eright\\u003c/b\\u003e onto \\u003cb\\u003eInffeldgasse\\u003c/b\\u003e\",\n" +
            "                     \"maneuver\" : \"turn-right\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"y_f~Genj}A^dAvBzHpAjEHZ|@jD\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 47.05805,\n" +
            "                        \"lng\" : 15.45971\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"WALKING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"0.2 km\",\n" +
            "                        \"value\" : 154\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"2 mins\",\n" +
            "                        \"value\" : 96\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 47.05549000000001,\n" +
            "                        \"lng\" : 15.45446\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Continue onto \\u003cb\\u003eNeufeldweg\\u003c/b\\u003e\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"gve~Goui}ABH@DBBd@\\\\pAdAZh@P\\\\LXFPFV\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 47.056517,\n" +
            "                        \"lng\" : 15.4557585\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"WALKING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"1.4 km\",\n" +
            "                        \"value\" : 1408\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"17 mins\",\n" +
            "                        \"value\" : 1031\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 47.0664259,\n" +
            "                        \"lng\" : 15.4468367\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Turn \\u003cb\\u003eright\\u003c/b\\u003e onto \\u003cb\\u003eMÃ¼nzgrabenstraÃ\u009Fe\\u003c/b\\u003e\",\n" +
            "                     \"maneuver\" : \"turn-right\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"yoe~Gkmi}AsCdCm@p@[Ra@PmAd@u@V{B`AkBp@aCz@aAT[DwAPa@DaBLc@D}BH_@@m@Jy@ZWPMH_Br@iA^{@^WZMXm@xCs@hDK^MV[\\\\}A~A]^mC`CeBhA\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 47.05549000000001,\n" +
            "                        \"lng\" : 15.45446\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"WALKING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"56 m\",\n" +
            "                        \"value\" : 56\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"1 min\",\n" +
            "                        \"value\" : 45\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 47.0667608,\n" +
            "                        \"lng\" : 15.4462913\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Slight \\u003cb\\u003eleft\\u003c/b\\u003e onto \\u003cb\\u003eDietrichsteinpl.\\u003c/b\\u003e\",\n" +
            "                     \"maneuver\" : \"turn-slight-left\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"etg~Gw}g}Ac@j@KXQf@\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 47.0664259,\n" +
            "                        \"lng\" : 15.4468367\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"WALKING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"0.2 km\",\n" +
            "                        \"value\" : 202\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"3 mins\",\n" +
            "                        \"value\" : 160\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 47.06712,\n" +
            "                        \"lng\" : 15.44368\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Continue onto \\u003cb\\u003eReitschulgasse\\u003c/b\\u003e\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"gvg~Gizg}AC`@OlCS`COfBOnB\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 47.0667608,\n" +
            "                        \"lng\" : 15.4462913\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"WALKING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"58 m\",\n" +
            "                        \"value\" : 58\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"1 min\",\n" +
            "                        \"value\" : 35\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 47.0673735,\n" +
            "                        \"lng\" : 15.4430285\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Continue straight onto \\u003cb\\u003eJakominipl.\\u003c/b\\u003e\",\n" +
            "                     \"maneuver\" : \"straight\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"oxg~G_jg}Ak@|ACFAF?D?D?F\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 47.06712,\n" +
            "                        \"lng\" : 15.44368\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"WALKING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"21 m\",\n" +
            "                        \"value\" : 21\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"1 min\",\n" +
            "                        \"value\" : 15\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 47.0672585,\n" +
            "                        \"lng\" : 15.442816\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Turn \\u003cb\\u003eleft\\u003c/b\\u003e to stay on \\u003cb\\u003eJakominipl.\\u003c/b\\u003e\",\n" +
            "                     \"maneuver\" : \"turn-left\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"azg~G}eg}A@H@FBFBFBBDB\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 47.0673735,\n" +
            "                        \"lng\" : 15.4430285\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"WALKING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"91 m\",\n" +
            "                        \"value\" : 91\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"1 min\",\n" +
            "                        \"value\" : 72\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 47.0668322,\n" +
            "                        \"lng\" : 15.4419836\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Turn \\u003cb\\u003eright\\u003c/b\\u003e to stay on \\u003cb\\u003eJakominipl.\\u003c/b\\u003e\",\n" +
            "                     \"maneuver\" : \"turn-right\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"kyg~Gsdg}AAH?J?H@FBLd@dBBDBBB@@@D@B@DALC\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 47.0672585,\n" +
            "                        \"lng\" : 15.442816\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"WALKING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"48 m\",\n" +
            "                        \"value\" : 48\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"1 min\",\n" +
            "                        \"value\" : 37\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 47.0667184,\n" +
            "                        \"lng\" : 15.4413825\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Turn \\u003cb\\u003eright\\u003c/b\\u003e to stay on \\u003cb\\u003eJakominipl.\\u003c/b\\u003e\",\n" +
            "                     \"maneuver\" : \"turn-right\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"uvg~Gk_g}AHb@BN@N@L@X@J\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 47.0668322,\n" +
            "                        \"lng\" : 15.4419836\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"WALKING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"0.2 km\",\n" +
            "                        \"value\" : 230\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"3 mins\",\n" +
            "                        \"value\" : 162\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 47.0663932,\n" +
            "                        \"lng\" : 15.4383864\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Continue onto \\u003cb\\u003eRadetzkystraÃ\u009Fe\\u003c/b\\u003e\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"_vg~Gs{f}ABV@J@N@R@`@^pHD~@NxB\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 47.0667184,\n" +
            "                        \"lng\" : 15.4413825\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"WALKING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"12 m\",\n" +
            "                        \"value\" : 12\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"1 min\",\n" +
            "                        \"value\" : 8\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 47.0664916,\n" +
            "                        \"lng\" : 15.4383245\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Turn \\u003cb\\u003eright\\u003c/b\\u003e onto \\u003cb\\u003eWielandgasse\\u003c/b\\u003e\",\n" +
            "                     \"maneuver\" : \"turn-right\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"}sg~G}hf}ASL\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 47.0663932,\n" +
            "                        \"lng\" : 15.4383864\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"WALKING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"0.4 km\",\n" +
            "                        \"value\" : 364\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"4 mins\",\n" +
            "                        \"value\" : 268\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 47.0694659,\n" +
            "                        \"lng\" : 15.4363476\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Continue onto \\u003cb\\u003eNeutorgasse\\u003c/b\\u003e\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"qtg~Gohf}AwAz@_Af@uCvAoDfBuBdB\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 47.0664916,\n" +
            "                        \"lng\" : 15.4383245\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"WALKING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"45 m\",\n" +
            "                        \"value\" : 45\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"1 min\",\n" +
            "                        \"value\" : 25\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 47.0695062,\n" +
            "                        \"lng\" : 15.4357594\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Turn \\u003cb\\u003eleft\\u003c/b\\u003e onto \\u003cb\\u003eAndreas-Hofer-Platz\\u003c/b\\u003e\",\n" +
            "                     \"maneuver\" : \"turn-left\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"egh~Ge|e}AAZAJ?HAHAx@\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 47.0694659,\n" +
            "                        \"lng\" : 15.4363476\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"WALKING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"84 m\",\n" +
            "                        \"value\" : 84\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"1 min\",\n" +
            "                        \"value\" : 76\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 47.069558,\n" +
            "                        \"lng\" : 15.4346557\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Continue onto \\u003cb\\u003eTegetthoffbrÃ¼cke\\u003c/b\\u003e\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"mgh~Goxe}A?RAJ?BAz@AXC`B\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 47.0695062,\n" +
            "                        \"lng\" : 15.4357594\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"WALKING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"89 m\",\n" +
            "                        \"value\" : 89\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"1 min\",\n" +
            "                        \"value\" : 73\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 47.0703549,\n" +
            "                        \"lng\" : 15.434643\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Turn \\u003cb\\u003eright\\u003c/b\\u003e onto \\u003cb\\u003eGrieskai\\u003c/b\\u003e\\u003cdiv style=\\\"font-size:0.9em\\\"\\u003eDestination will be on the left\\u003c/div\\u003e\",\n" +
            "                     \"maneuver\" : \"turn-right\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"wgh~Gsqe}AcBAy@D\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 47.069558,\n" +
            "                        \"lng\" : 15.4346557\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"WALKING\"\n" +
            "                  }\n" +
            "               ],\n" +
            "               \"via_waypoint\" : []\n" +
            "            },\n" +
            "            {\n" +
            "               \"distance\" : {\n" +
            "                  \"text\" : \"0.9 km\",\n" +
            "                  \"value\" : 943\n" +
            "               },\n" +
            "               \"duration\" : {\n" +
            "                  \"text\" : \"12 mins\",\n" +
            "                  \"value\" : 729\n" +
            "               },\n" +
            "               \"end_address\" : \"Jakominiplatz 16, 8010 Graz, Austria\",\n" +
            "               \"end_location\" : {\n" +
            "                  \"lat\" : 47.0672434,\n" +
            "                  \"lng\" : 15.4425523\n" +
            "               },\n" +
            "               \"start_address\" : \"Grieskai 6, 8020 Graz, Austria\",\n" +
            "               \"start_location\" : {\n" +
            "                  \"lat\" : 47.0703549,\n" +
            "                  \"lng\" : 15.434643\n" +
            "               },\n" +
            "               \"steps\" : [\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"89 m\",\n" +
            "                        \"value\" : 89\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"1 min\",\n" +
            "                        \"value\" : 60\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 47.069558,\n" +
            "                        \"lng\" : 15.4346557\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Head \\u003cb\\u003esouth\\u003c/b\\u003e on \\u003cb\\u003eGrieskai\\u003c/b\\u003e toward \\u003cb\\u003eIgelgasse\\u003c/b\\u003e\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"ulh~Goqe}Ax@EbB@\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 47.0703549,\n" +
            "                        \"lng\" : 15.434643\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"WALKING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"84 m\",\n" +
            "                        \"value\" : 84\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"1 min\",\n" +
            "                        \"value\" : 64\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 47.0695062,\n" +
            "                        \"lng\" : 15.4357594\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Turn \\u003cb\\u003eleft\\u003c/b\\u003e onto \\u003cb\\u003eTegetthoffbrÃ¼cke\\u003c/b\\u003e\",\n" +
            "                     \"maneuver\" : \"turn-left\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"wgh~Gsqe}ABaB@Y@{@?C@K?S\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 47.069558,\n" +
            "                        \"lng\" : 15.4346557\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"WALKING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"45 m\",\n" +
            "                        \"value\" : 45\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"1 min\",\n" +
            "                        \"value\" : 64\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 47.0694659,\n" +
            "                        \"lng\" : 15.4363476\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Continue onto \\u003cb\\u003eAndreas-Hofer-Platz\\u003c/b\\u003e\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"mgh~Goxe}A@y@@I?I@K@[\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 47.0695062,\n" +
            "                        \"lng\" : 15.4357594\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"WALKING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"0.4 km\",\n" +
            "                        \"value\" : 364\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"4 mins\",\n" +
            "                        \"value\" : 269\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 47.0664916,\n" +
            "                        \"lng\" : 15.4383245\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Turn \\u003cb\\u003eright\\u003c/b\\u003e onto \\u003cb\\u003eNeutorgasse\\u003c/b\\u003e\",\n" +
            "                     \"maneuver\" : \"turn-right\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"egh~Ge|e}AtBeBnDgBtCwA~@g@vA{@\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 47.0694659,\n" +
            "                        \"lng\" : 15.4363476\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"WALKING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"12 m\",\n" +
            "                        \"value\" : 12\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"1 min\",\n" +
            "                        \"value\" : 19\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 47.0663932,\n" +
            "                        \"lng\" : 15.4383864\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Continue onto \\u003cb\\u003eWielandgasse\\u003c/b\\u003e\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"qtg~Gohf}ARM\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 47.0664916,\n" +
            "                        \"lng\" : 15.4383245\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"WALKING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"0.2 km\",\n" +
            "                        \"value\" : 230\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"3 mins\",\n" +
            "                        \"value\" : 174\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 47.0667184,\n" +
            "                        \"lng\" : 15.4413825\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Turn \\u003cb\\u003eleft\\u003c/b\\u003e onto \\u003cb\\u003eRadetzkystraÃ\u009Fe\\u003c/b\\u003e\",\n" +
            "                     \"maneuver\" : \"turn-left\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"}sg~G}hf}AOyBE_A_@qHAa@ASAOAKCW\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 47.0663932,\n" +
            "                        \"lng\" : 15.4383864\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"WALKING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"48 m\",\n" +
            "                        \"value\" : 48\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"1 min\",\n" +
            "                        \"value\" : 32\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 47.0668322,\n" +
            "                        \"lng\" : 15.4419836\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Continue onto \\u003cb\\u003eJakominipl.\\u003c/b\\u003e\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"_vg~Gs{f}AAKAYAMAOCOIc@\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 47.0667184,\n" +
            "                        \"lng\" : 15.4413825\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"WALKING\"\n" +
            "                  },\n" +
            "                  {\n" +
            "                     \"distance\" : {\n" +
            "                        \"text\" : \"71 m\",\n" +
            "                        \"value\" : 71\n" +
            "                     },\n" +
            "                     \"duration\" : {\n" +
            "                        \"text\" : \"1 min\",\n" +
            "                        \"value\" : 47\n" +
            "                     },\n" +
            "                     \"end_location\" : {\n" +
            "                        \"lat\" : 47.0672434,\n" +
            "                        \"lng\" : 15.4425523\n" +
            "                     },\n" +
            "                     \"html_instructions\" : \"Turn \\u003cb\\u003eleft\\u003c/b\\u003e to stay on \\u003cb\\u003eJakominipl.\\u003c/b\\u003e\",\n" +
            "                     \"maneuver\" : \"turn-left\",\n" +
            "                     \"polyline\" : {\n" +
            "                        \"points\" : \"uvg~Gk_g}AMBE@CAEAAACACCCEe@eB\"\n" +
            "                     },\n" +
            "                     \"start_location\" : {\n" +
            "                        \"lat\" : 47.0668322,\n" +
            "                        \"lng\" : 15.4419836\n" +
            "                     },\n" +
            "                     \"travel_mode\" : \"WALKING\"\n" +
            "                  }\n" +
            "               ],\n" +
            "               \"via_waypoint\" : []\n" +
            "            }\n" +
            "         ],\n" +
            "         \"overview_polyline\" : {\n" +
            "            \"points\" : \"cbf~Gwlj}Af@Q`@[^dAhEfOfAfEDNh@`@pAdAZh@^v@Nh@sCdCm@p@}@d@cC|@{B`AkBp@aCz@aATsBVcCRaDN_@@m@Jy@ZWPmB|@iA^{@^WZMXaBbIYv@yB|BkD`DeBhAc@j@]`ASnDc@hFOnBk@|AEN@\\\\LZDBAH?TDTh@jBNHH?LCHb@D^Bf@H~@h@fLNxBSLwCbBeI~DuBdBAZATCbAC~AEzBcBAy@Dx@EbB@D{BB_BDyA@[tBeBdI_EvCcBRMOyBe@qJCu@I_AIgAIc@MBI?OIi@kB\"\n" +
            "         },\n" +
            "         \"summary\" : \"MÃ¼nzgrabenstraÃ\u009Fe\",\n" +
            "         \"warnings\" : [\n" +
            "            \"Walking directions are in beta.    Use caution â\u0080\u0093 This route may be missing sidewalks or pedestrian paths.\"\n" +
            "         ],\n" +
            "         \"waypoint_order\" : [ 0 ]\n" +
            "      }\n" +
            "   ],\n" +
            "   \"status\" : \"OK\"\n" +
            "}";
}
