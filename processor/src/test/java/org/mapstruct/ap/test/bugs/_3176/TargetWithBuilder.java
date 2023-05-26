package org.mapstruct.ap.test.bugs._3176;

public class TargetWithBuilder {

	private String example;

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	private TargetWithBuilder(Builder builder) {
		this.example = builder.example;
	}
	

	public TargetWithBuilder() {
		
	}
	
	public static Builder builder() {
		return new Builder();
	}


	public static class Builder {

		private String example;

		public Builder example(String example) {
			this.example = example;
			return this;
		}
		
		public String getExample() {
			return this.example;
		}

		public TargetWithBuilder build() {
			return new TargetWithBuilder(this);
		}

	}

}
