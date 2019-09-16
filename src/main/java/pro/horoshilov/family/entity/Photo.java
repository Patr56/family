package pro.horoshilov.family.entity;

/**
 * Фотография.
 */
public class Photo {
	/** Идентификатор. */
	private Long id;

	/** Ссылки на фото. */
	private Link link;

	public Photo(final Long id, final Link link) {
		this.id = id;
		this.link = link;
	}

	public Link getLink() {
		return link;
	}

	public Long getId() {
		return id;
	}

	public static class Link {
		/** Ссылка на фронтальную часть фото. */
		private String front;

		/** Ссылка на заднюю часть фото. */
		private String back;

		public Link(String front, String back) {
			this.front = front;
			this.back = back;
		}

		public String getFront() {
			return front;
		}

		public String getBack() {
			return back;
		}
	}
}
