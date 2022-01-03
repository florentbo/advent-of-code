package be.bonamis.advent.year2021;

import static be.bonamis.advent.year2021.Day17.TargetArea.Limit;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import be.bonamis.advent.DaySolver;
import be.bonamis.advent.common.Point3D;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class Day22 extends DaySolver<String> {

    private final List<Cuboid> cuboids = this.puzzle.stream().map(Cuboid::new).collect(Collectors.toList());

    public Day22(List<String> puzzle) {
        super(puzzle);
    }

    @Override
    public long solvePart01() {
        return step(cuboids.size() - 1).size();
    }

    Set<Point3D> step(int numberOfSteps) {
        Set<Point3D> cubesAfterStep = new HashSet<>();

        for (int i = 0; i <= numberOfSteps; i++) {
            Cuboid cuboid = cuboids.get(i);

            Set<Point3D> cubes = cuboid.getCubes();
            if (cuboid.getState() == Cuboid.State.OFF) {
                cubesAfterStep.removeAll(cubes);
            } else {
                cubesAfterStep.addAll(cubes);
            }
        }

        return cubesAfterStep;
    }

    @Override
    public long solvePart02() {
        return this.puzzle.size() + 1;
    }

    @Getter
	public static class Cuboid {
        private final Limit xLimit;
        private final Limit yLimit;
        private final Limit zLimit;
        private final Set<Point3D> cubes;
        private final State state;

        enum State {
            ON, OFF
        }

        public Cuboid(String input) {
            int xPosition = input.indexOf('x');

            this.state = input.charAt(1) == 'n' ? State.ON : State.OFF;

            String limits = input.substring(xPosition);
            final var split = limits.split(",");

            this.xLimit = new Limit(split[0].substring(2));
            this.yLimit = new Limit(split[1].substring(2));
            this.zLimit = new Limit(split[2].substring(2));

            Set<Point3D> set = new HashSet<>();

            for (int x = Math.max(this.xLimit.getMin(), -50); x <= Math.min(this.xLimit.getMax(), 50); x++) {
                for (int y = Math.max(this.yLimit.getMin(), -50); y <= Math.min(this.yLimit.getMax(), 50); y++) {
                    for (int z = Math.max(this.zLimit.getMin(), -50); z <= Math.min(this.zLimit.getMax(), 50); z++) {
                        set.add(new Point3D(x, y, z));
                    }
                }
            }

            this.cubes = set;
        }

        @Override
        public String toString() {
            return "Cuboid{" +
                    "xLimit=" + xLimit +
                    ", yLimit=" + yLimit +
                    ", zLimit=" + zLimit +
                    ", state=" + state +
                    '}';
        }
    }
}

