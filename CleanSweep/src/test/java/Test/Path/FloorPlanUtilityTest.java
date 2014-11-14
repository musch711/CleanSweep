package Test.Path;

import java.io.BufferedReader;
import java.io.StringReader;

import org.w3c.dom.Document;

import DePaul.SE459.CleanSweep.Floor;
import DePaul.SE459.CleanSweep.FloorPlan;
import DePaul.SE459.CleanSweep.Tile;
import DePaul.SE459.Simulator.FloorPlanUtility;
import junit.framework.TestCase;

public class FloorPlanUtilityTest extends TestCase {

	public FloorPlanUtilityTest(String name) {
		super(name);
	}

	public void testLoadFloorPlan() throws Exception {
		String testFile = prepareTestFile();

		Document doc = FloorPlanUtility.loadFromString(testFile);
		FloorPlan floorPlan = FloorPlanUtility.loadFloorPlan(doc);

		BufferedReader reader = new BufferedReader(new StringReader(testFile));

		String line = reader.readLine();
		assertNotNull(line);
		assertEquals("<?xml version=\"1.0\" encoding=\"utf-8\"?>", line.trim());
		System.out.println(line);

		line = reader.readLine();
		assertNotNull(line);
		assertEquals("<home>", line.trim());
		System.out.println(line);

		Floor floor = null;
		while (true) {
			line = reader.readLine();

			if (line == null) {
				break;
			}

			// if this is a floor, test the floor
			if (line.contains("<floor")) {
				System.out.println(line);
				assertTrue(line.contains("level='"));
				String level = line.substring(line.indexOf("level='") + 7, line.indexOf("'>"));
				int lvl = Integer.parseInt(level);

				boolean floorIsPresent = false;
				for (int i = 0; i < floorPlan.numberOfFloors(); i++) {
					floor = floorPlan.getFloor(i);

					if (floor.getLevel() == lvl) {
						floorIsPresent = true;
						break;
					}
				}

				assertTrue(floorIsPresent);

			}
			// if this is a cell, test the cell
			else if (line.contains("<cell")) {
				System.out.println(line);

				int xsIndex = line.indexOf("xs='") + 4;
				String xs = line.substring(xsIndex, line.indexOf("'", xsIndex));
				int xPos = Integer.parseInt(xs);

				int ysIndex = line.indexOf("ys='") + 4;
				String ys = line.substring(ysIndex, line.indexOf("'", ysIndex));
				int yPos = Integer.parseInt(ys);

				assertNotNull(floor);
				Tile tile = floor.getTile(xPos, yPos);
				assertNotNull(tile);

				int ssIndex = line.indexOf("ss='") + 4;
				String ss = line.substring(ssIndex, line.indexOf("'", ssIndex));
				int surface = Integer.parseInt(ss);
				
				assertEquals(surface, tile.getSurfaceType());

				int psIndex = line.indexOf("ps='") + 4;
				String paths = line.substring(psIndex, line.indexOf("'", psIndex));
				
				int right = Integer.parseInt(paths.substring(0, 1));
				int left = Integer.parseInt(paths.substring(1, 2));
				int up = Integer.parseInt(paths.substring(2, 3));
				int down = Integer.parseInt(paths.substring(3, 4));

				assertEquals(right, tile.getRightPath());
				assertEquals(left, tile.getLeftPath());
				assertEquals(up, tile.getUpperPath());
				assertEquals(down, tile.getLowerPath());
				
				int dsIndex = line.indexOf("ds='") + 4;
				String ds = line.substring(dsIndex, line.indexOf("'", dsIndex));
				int dirt = Integer.parseInt(ds);
				assertEquals(dirt, tile.getDirtAmount());
				
				int csIndex = line.indexOf("cs='") + 4;
				String cs = line.substring(csIndex, line.indexOf("'", csIndex));
				int home = Integer.parseInt(cs);
				
				boolean isHome;				
				if (home == 0)
				{
					isHome = false;
				}
				else
				{
					isHome = true;
				}
				
				assertNotNull(home);
				assertEquals(isHome, tile.isChargingStation());
			}
			// if this is neither floor nor cell, ignore it
		}

		try {
			reader.close();
		} catch (Exception e) {
			// at least we tried
		}
	}

	private String prepareTestFile() {
		return new StringBuilder().append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n")
				.append("    		<home>\n")
				.append("			<floor level='1'>\n").append("<!-- Guest Bedroom A -->\n")
				.append("				<cell xs='0' ys='0' ss='2' ps='1212' ds='1' cs='1' />\n")
				.append("				<cell xs='1' ys='0' ss='2' ps='1112' ds='1' cs='0' />\n")
				.append("				<cell xs='2' ys='0' ss='2' ps='1112' ds='1' cs='0' />\n")
				.append("				<cell xs='3' ys='0' ss='2' ps='2112' ds='1' cs='0' />\n")
				.append("				<cell xs='0' ys='1' ss='2' ps='1211' ds='1' cs='0' />\n")
				.append("				<cell xs='1' ys='1' ss='2' ps='1111' ds='1' cs='0' />\n")
				.append("				<cell xs='2' ys='1' ss='2' ps='1111' ds='1' cs='0' />\n")
				.append("				<cell xs='3' ys='1' ss='2' ps='2111' ds='1' cs='0' />\n")
				.append("				<cell xs='0' ys='2' ss='2' ps='1211' ds='1' cs='0' />\n")
				.append("				<cell xs='1' ys='2' ss='2' ps='1111' ds='1' cs='0' />\n")
				.append("				<cell xs='2' ys='2' ss='2' ps='1111' ds='1' cs='0' />\n")
				.append("				<cell xs='3' ys='2' ss='2' ps='2111' ds='1' cs='0' />\n")
				.append("				<cell xs='0' ys='3' ss='2' ps='1211' ds='1' cs='0' />\n")
				.append("				<cell xs='1' ys='3' ss='2' ps='1111' ds='1' cs='0' />\n")
				.append("				<cell xs='2' ys='3' ss='2' ps='1111' ds='1' cs='0' />\n")
				.append("				<cell xs='3' ys='3' ss='2' ps='1111' ds='1' cs='0' />\n")
				.append("				<cell xs='0' ys='4' ss='2' ps='1221' ds='1' cs='0' />\n")
				.append("				<cell xs='1' ys='4' ss='2' ps='1121' ds='1' cs='0' />\n")
				.append("				<cell xs='2' ys='4' ss='2' ps='1121' ds='1' cs='0' />\n")
				.append("				<cell xs='3' ys='4' ss='2' ps='2121' ds='1' cs='0' />\n")
				.append("\n").append("				<!-- Hallway -->\n")
				.append("				<cell xs='4' ys='0' ss='1' ps='1214' ds='2' cs='0' />\n")
				.append("				<cell xs='5' ys='0' ss='1' ps='2112' ds='1' cs='0' />\n")
				.append("				<cell xs='4' ys='1' ss='1' ps='1211' ds='2' cs='0' />\n")
				.append("				<cell xs='5' ys='1' ss='1' ps='2111' ds='1' cs='0' />\n")
				.append("				<cell xs='4' ys='2' ss='1' ps='1211' ds='2' cs='0' />\n")
				.append("				<cell xs='5' ys='2' ss='1' ps='2111' ds='1' cs='0' />\n")
				.append("				<cell xs='4' ys='3' ss='1' ps='1111' ds='2' cs='0' />\n")
				.append("				<cell xs='5' ys='3' ss='1' ps='2111' ds='1' cs='0' />\n")
				.append("				<cell xs='4' ys='4' ss='1' ps='1211' ds='2' cs='0' />\n")
				.append("				<cell xs='5' ys='4' ss='1' ps='2111' ds='1' cs='0' />\n")
				.append("				<cell xs='4' ys='5' ss='1' ps='1211' ds='2' cs='0' />\n")
				.append("				<cell xs='5' ys='5' ss='1' ps='2111' ds='1' cs='0' />\n")
				.append("				<cell xs='4' ys='6' ss='1' ps='1211' ds='2' cs='0' />\n")
				.append("				<cell xs='5' ys='6' ss='1' ps='2111' ds='1' cs='0' />\n")
				.append("				<cell xs='4' ys='7' ss='1' ps='1121' ds='2' cs='0' />\n")
				.append("				<cell xs='5' ys='7' ss='1' ps='1121' ds='3' cs='0' />\n")
				.append("\n").append("				<!-- Guest Bedroom B -->\n")
				.append("				<cell xs='0' ys='7' ss='2' ps='1212' ds='1' cs='0' />\n")
				.append("				<cell xs='1' ys='7' ss='2' ps='1112' ds='1' cs='0' />\n")
				.append("				<cell xs='2' ys='7' ss='2' ps='1112' ds='1' cs='0' />\n")
				.append("				<cell xs='3' ys='7' ss='2' ps='1112' ds='1' cs='0' />\n")
				.append("				<cell xs='0' ys='8' ss='2' ps='1211' ds='1' cs='0' />\n")
				.append("				<cell xs='1' ys='8' ss='2' ps='1111' ds='1' cs='0' />\n")
				.append("				<cell xs='2' ys='8' ss='2' ps='1111' ds='1' cs='0' />\n")
				.append("				<cell xs='3' ys='8' ss='2' ps='2111' ds='1' cs='0' />\n")
				.append("				<cell xs='0' ys='9' ss='2' ps='1221' ds='1' cs='0' />\n")
				.append("				<cell xs='1' ys='9' ss='2' ps='1121' ds='1' cs='0' />\n")
				.append("				<cell xs='2' ys='9' ss='2' ps='1121' ds='1' cs='0' />\n")
				.append("				<cell xs='3' ys='9' ss='2' ps='2121' ds='1' cs='0' />\n")
				.append("\n").append("				<!-- Master Bedroom -->\n")
				.append("				<cell xs='6' ys='0' ss='1' ps='1212' ds='1' cs='0' />\n")
				.append("				<cell xs='7' ys='0' ss='1' ps='1112' ds='1' cs='0' />\n")
				.append("				<cell xs='8' ys='0' ss='1' ps='1112' ds='1' cs='0' />\n")
				.append("				<cell xs='9' ys='0' ss='1' ps='2112' ds='1' cs='0' />\n")
				.append("				<cell xs='6' ys='1' ss='1' ps='1211' ds='1' cs='0' />\n")
				.append("				<cell xs='7' ys='1' ss='4' ps='1111' ds='2' cs='0' />\n")
				.append("				<cell xs='8' ys='1' ss='4' ps='1111' ds='2' cs='0' />\n")
				.append("				<cell xs='9' ys='1' ss='1' ps='2111' ds='1' cs='0' />\n")
				.append("				<cell xs='6' ys='2' ss='1' ps='1211' ds='1' cs='0' />\n")
				.append("				<cell xs='7' ys='2' ss='4' ps='1111' ds='2' cs='0' />\n")
				.append("				<cell xs='8' ys='2' ss='4' ps='1111' ds='2' cs='0' />\n")
				.append("				<cell xs='9' ys='2' ss='1' ps='2111' ds='1' cs='0' />\n")
				.append("				<cell xs='6' ys='3' ss='1' ps='1211' ds='1' cs='0' />\n")
				.append("				<cell xs='7' ys='3' ss='4' ps='1111' ds='2' cs='0' />\n")
				.append("				<cell xs='8' ys='3' ss='4' ps='1111' ds='2' cs='0' />\n")
				.append("				<cell xs='9' ys='3' ss='1' ps='2111' ds='1' cs='0' />\n")
				.append("				<cell xs='6' ys='4' ss='1' ps='1211' ds='1' cs='0' />\n")
				.append("				<cell xs='7' ys='4' ss='4' ps='1111' ds='2' cs='0' />\n")
				.append("				<cell xs='8' ys='4' ss='4' ps='1111' ds='2' cs='0' />\n")
				.append("				<cell xs='9' ys='4' ss='1' ps='2111' ds='1' cs='0' />\n")
				.append("				<cell xs='6' ys='5' ss='1' ps='1211' ds='1' cs='0' />\n")
				.append("				<cell xs='7' ys='5' ss='4' ps='1111' ds='1' cs='0' />\n")
				.append("				<cell xs='8' ys='5' ss='4' ps='1111' ds='1' cs='0' />\n")
				.append("				<cell xs='9' ys='5' ss='1' ps='2111' ds='1' cs='0' />\n")
				.append("				<cell xs='6' ys='6' ss='1' ps='1211' ds='1' cs='0' />\n")
				.append("				<cell xs='7' ys='6' ss='1' ps='1111' ds='1' cs='0' />\n")
				.append("				<cell xs='8' ys='6' ss='1' ps='1121' ds='1' cs='0' />\n")
				.append("				<cell xs='9' ys='6' ss='1' ps='2121' ds='1' cs='0' />\n")
				.append("				<cell xs='6' ys='7' ss='1' ps='1111' ds='1' cs='0' />\n")
				.append("				<cell xs='7' ys='7' ss='1' ps='2111' ds='1' cs='0' />\n")
				.append("				<cell xs='6' ys='8' ss='1' ps='1221' ds='1' cs='0' />\n")
				.append("				<cell xs='7' ys='8' ss='1' ps='2121' ds='1' cs='0' />\n")
				.append("			</floor>                                                 \n")
				.append("			<floor level='2'>                                        \n")
				.append("				<cell xs='6' ys='0' ss='1' ps='1212' ds='1' cs='0' />\n")
				.append("				<cell xs='7' ys='0' ss='1' ps='1112' ds='1' cs='0' />\n")
				.append("				<cell xs='8' ys='0' ss='1' ps='1112' ds='1' cs='0' />\n")
				.append("				<cell xs='9' ys='0' ss='1' ps='2112' ds='1' cs='0' />\n")
				.append("				<cell xs='6' ys='1' ss='1' ps='1211' ds='1' cs='0' />\n")
				.append("				<cell xs='7' ys='1' ss='4' ps='1111' ds='2' cs='0' />\n")
				.append("				<cell xs='8' ys='1' ss='4' ps='1111' ds='2' cs='0' />\n")
				.append("				<cell xs='9' ys='1' ss='1' ps='2111' ds='1' cs='0' />\n")
				.append("				<cell xs='6' ys='2' ss='1' ps='1211' ds='1' cs='0' />\n")
				.append("				<cell xs='7' ys='2' ss='4' ps='1111' ds='2' cs='0' />\n")
				.append("				<cell xs='8' ys='2' ss='4' ps='1111' ds='2' cs='0' />\n")
				.append("				<cell xs='9' ys='2' ss='1' ps='2111' ds='1' cs='0' />\n")
				.append("				<cell xs='6' ys='3' ss='1' ps='1211' ds='1' cs='0' />\n")
				.append("				<cell xs='7' ys='3' ss='4' ps='1111' ds='2' cs='0' />\n")
				.append("				<cell xs='8' ys='3' ss='4' ps='1111' ds='2' cs='0' />\n")
				.append("				<cell xs='9' ys='3' ss='1' ps='2111' ds='1' cs='0' />\n")
				.append("				<cell xs='6' ys='4' ss='1' ps='1211' ds='1' cs='0' />\n")
				.append("				<cell xs='7' ys='4' ss='4' ps='1111' ds='2' cs='0' />\n")
				.append("				<cell xs='8' ys='4' ss='4' ps='1111' ds='2' cs='0' />\n")
				.append("				<cell xs='9' ys='4' ss='1' ps='2111' ds='1' cs='0' />\n")
				.append("				<cell xs='6' ys='5' ss='1' ps='1211' ds='1' cs='0' />\n")
				.append("				<cell xs='7' ys='5' ss='4' ps='1111' ds='1' cs='0' />\n")
				.append("				<cell xs='8' ys='5' ss='4' ps='1121' ds='1' cs='0' />\n")
				.append("				<cell xs='9' ys='5' ss='1' ps='2121' ds='1' cs='0' />\n")
				.append("				<cell xs='6' ys='6' ss='1' ps='1211' ds='1' cs='0' />\n")
				.append("				<cell xs='7' ys='6' ss='1' ps='1111' ds='1' cs='0' />\n")
				.append("				<cell xs='8' ys='6' ss='1' ps='1121' ds='1' cs='0' />\n")
				.append("				<cell xs='9' ys='6' ss='1' ps='2121' ds='1' cs='0' />\n")
				.append("				<cell xs='6' ys='7' ss='1' ps='1211' ds='1' cs='0' />\n")
				.append("				<cell xs='7' ys='7' ss='1' ps='2111' ds='1' cs='0' />\n")
				.append("				<cell xs='6' ys='8' ss='1' ps='1221' ds='1' cs='0' />\n")
				.append("				<cell xs='7' ys='8' ss='1' ps='2121' ds='19' cs='0' />\n")
				.append("			</floor>\n")
				.append("		</home>\n").toString();
	}
}
