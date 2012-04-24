package com.mulesfot.lookuptable.persistence.service.response.objecstore;

import java.util.List;

public class GetAllObjectStoreResponse {

	public static class GetAllItem {
		private String path;
		private String key;
		private String value;

		public GetAllItem(String path, String key, String value) {
			super();
			this.path = path;
			this.key = key;
			this.value = value;
		}

		public String getPath() {
			return path;
		}

		public String getKey() {
			return key;
		}

		public String getValue() {
			return value;
		}

	}

	private List<GetAllItem> data;
	private int total;
	private int next_offset;

	public GetAllObjectStoreResponse(List<GetAllItem> data, int total, int next_offset) {
		super();
		this.data = data;
		this.total = total;
		this.next_offset = next_offset;
	}

	public List<GetAllItem> getData() {
		return data;
	}

	public int getTotal() {
		return total;
	}

	public int getNext_offset() {
		return next_offset;
	}

}
