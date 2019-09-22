package pro.horoshilov.family.entity;

/**
 * Информация о фото.
 */
public class PhotoInformation {
    /** Фотография. */
    private Photo photo;

    /** Человек. */
    private Person person;

    /** Область на фото. */
    private Area area;

    /** Описание. */
    private String description;

    public PhotoInformation(
            final Photo photo,
            final Person person,
            final Area area,
            final String description
    ) {
        this.photo = photo;
        this.person = person;
        this.area = area;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public Area getArea() {
        return area;
    }

    public Person getPerson() {
        return person;
    }

    public Photo getPhoto() {
        return photo;
    }

    public static class Area {
        /** Координаты верхнего левого угла. */
        private Coordinate topLeft;

        /** Координаты нижнего правого угла. */
        private Coordinate bottomRight;

        public Area(final Coordinate topLeft, final Coordinate bottomRight) {
            this.topLeft = topLeft;
            this.bottomRight = bottomRight;
        }

        public Coordinate getTopLeft() {
            return topLeft;
        }

        public Coordinate getBottomRight() {
            return bottomRight;
        }

        public static class Coordinate {
            /** Положение по оси X. */
            private double x;

            /** Положение по оси Y. */
            private double y;

            public Coordinate(final double x, final double y) {
                this.x = x;
                this.y = y;
            }

            public double getX() {
                return x;
            }

            public double getY() {
                return y;
            }
        }
    }
}
