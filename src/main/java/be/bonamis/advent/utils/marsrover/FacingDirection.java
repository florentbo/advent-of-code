package be.bonamis.advent.utils.marsrover;

public enum FacingDirection {
	NORTH(0, 1),
	SOUTH(0, -1),
	WEST(-1, 0),
	EAST(1, 0);

	private final int forwardMoveY;
	private final int forwardMoveX;

	FacingDirection(int x, int y) {
		this.forwardMoveX = x;
		this.forwardMoveY = y;
	}

	public int getForwardMoveY() {
		return forwardMoveY;
	}

	public int getForwardMoveX() {
		return forwardMoveX;
	}

	public FacingDirection inverse() {
		return switch (this) {
			case NORTH -> FacingDirection.SOUTH;
			case SOUTH -> FacingDirection.NORTH;
			case WEST -> FacingDirection.EAST;
			case EAST -> FacingDirection.WEST;
		};
	}
}
