package sem.group47.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import sem.group47.audio.AudioPlayer;
import sem.group47.main.Log;
import sem.group47.tilemap.TileMap;

/**
 * The Class PlayerTest.
 */
public class PlayerTest {

	/** The tile map. */
	private TileMap tileMap;

	/** The tile size. */
	private int tileSize = 30;

	/** The num of cols. */
	private int numOfCols = 2;

	/** The num of rows. */
	private int numOfRows = 2;

	/** The player. */
	private Player player;

	/** The player save state. */
	private PlayerSave playerSave;

	/** The projectile. */
	private Projectile projectile;

	/**
	 * SetUp.
	 *
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	@Before
	public final void setUp() throws IOException {

		Log.setLog();
		AudioPlayer.init();

		tileMap = new TileMap(tileSize);

		tileMap.loadTiles("/test/Test_Tile.gif");
		tileMap.loadMap("/test/Test_Map.map");
		player = new Player(tileMap);
		projectile = new Projectile(tileMap);

	}

	/**
	 * Update test.
	 */
	@Test
	public final void updateTest() {
		player.setDown(true);
		player.update();
		assertEquals(player.getProjectiles().size(), 1);

	}

	/**
	 * Hit test.
	 */
	@Test
	public final void hitTest() {
		player.hit(1);
		assertEquals(playerSave.getLives(), 3);
	}

	/**
	 * Hit dead test.
	 */
	@Test
	public final void hitDeadTest() {
		player.hit(3);
		assertEquals(player.getIsAlive(), false);
		assertEquals(player.getLives(), 0);
	}

	/**
	 * Hit out of bounds test.
	 */
	@Test
	public final void hitOutOfBoundsTest() {
		player.hit(4);
		assertEquals(player.getIsAlive(), false);
		assertEquals(player.getLives(), 0);
	}

	/**
	 * Hit flinch test.
	 */
	@Test
	public final void hitFlinchTest() {
		player.setFlinch(true);
		player.hit(1);
		assertEquals(playerSave.getLives(), 3);
	}

	/**
	 * Next position left test.
	 */
	@Test
	public final void nextPositionLeftTest() {
		player.setLeft(true);
		player.setMovSpeed(3.0);
		player.setMaxSpeed(2.0);
		player.getNextXPosition();
		assertEquals(player.getDx(), -2.0, 0.1);
	}

	/**
	 * Next position right test.
	 */
	@Test
	public final void nextPositionRightTest() {
		player.setRight(true);
		player.setMovSpeed(3.0);
		player.setMaxSpeed(2.0);
		player.getNextXPosition();
		assertEquals(player.getDx(), 2.0, 0.1);
	}

	/**
	 * Next position stop test.
	 */
	@Test
	public final void nextPositionStopTest() {
		player.setRight(false);
		player.setLeft(false);
		player.getNextXPosition();
		assertEquals(player.getDx(), 0, 0);
	}

	/**
	 * Next position up test.
	 */
	@Test
	public final void nextPositionUpTest() {
		player.setUp(true);
		player.getNextYPosition();
		assertTrue(player.isJumping());
	}

	/**
	 * Next position falling test.
	 */
	@Test
	public final void nextPositionFallingTest() {
		player.setFallSpeed(1);
		player.setFalling(true);
		player.getNextYPosition();
		assertTrue(!player.isJumping());
	}
}
