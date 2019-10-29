package pro.horoshilov.family.entity;

import java.util.Objects;

/**
 * Информация о фото.
 */
public class PhotoInformation {
    /** Фотография. */
    private Long id;

    /** Фотография. */
    private Long photoId;

    /** Человек. */
    private Long personId;

    /** Область на фото. */
    private Area area;

    /** Описание. */
    private String description;

    public PhotoInformation() {
    }

    public PhotoInformation(
            final Long id,
            final Long photoId,
            final Long personId,
            final Area area,
            final String description
    ) {
        this.id = id;
        this.photoId = photoId;
        this.personId = personId;
        this.area = area;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Area getArea() {
        return area;
    }

    public Long getPersonId() {
        return personId;
    }

    public Long getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Long photoId) {
        this.photoId = photoId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class Area {
        /** Координаты верхнего левого угла. */
        private Coordinate topLeft;

        /** Координаты нижнего правого угла. */
        private Coordinate bottomRight;

        public Area() {
        }

        public void setTopLeft(Coordinate topLeft) {
            this.topLeft = topLeft;
        }

        public void setBottomRight(Coordinate bottomRight) {
            this.bottomRight = bottomRight;
        }

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
            private Double x;

            /** Положение по оси Y. */
            private Double y;

            public Coordinate() {
            }

            public Coordinate(final Double x, final Double y) {
                this.x = x;
                this.y = y;
            }

            public Double getX() {
                return x;
            }

            public Double getY() {
                return y;
            }

            public void setX(double x) {
                this.x = x;
            }

            public void setY(double y) {
                this.y = y;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Coordinate that = (Coordinate) o;
                return Double.compare(that.x, x) == 0 &&
                        Double.compare(that.y, y) == 0;
            }

            @Override
            public int hashCode() {
                return Objects.hash(x, y);
            }

            @Override
            public String toString() {
                return "Coordinate{" +
                        "x=" + x +
                        ", y=" + y +
                        '}';
            }
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Area area = (Area) o;
            return Objects.equals(topLeft, area.topLeft) &&
                    Objects.equals(bottomRight, area.bottomRight);
        }

        @Override
        public int hashCode() {
            return Objects.hash(topLeft, bottomRight);
        }

        @Override
        public String toString() {
            return "Area{" +
                    "topLeft=" + topLeft +
                    ", bottomRight=" + bottomRight +
                    '}';
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhotoInformation that = (PhotoInformation) o;
        return Objects.equals(photoId, that.photoId) &&
                Objects.equals(personId, that.personId) &&
                Objects.equals(area, that.area) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(photoId, personId, area, description);
    }

    @Override
    public String toString() {
        return "PhotoInformation{" +
                "photoId=" + photoId +
                ", personId=" + personId +
                ", area=" + area +
                ", description='" + description + '\'' +
                '}';
    }
}
