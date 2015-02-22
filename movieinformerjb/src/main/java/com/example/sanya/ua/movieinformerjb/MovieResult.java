package com.example.sanya.ua.movieinformerjb;

public class MovieResult {
    private final String backdropPath;
    private final String originalTitle;
    private final int id;
    private final double popularity;
    private final String posterPath;
    private final String releaseDate;
    private final String title;
    private final double voteAverage;
    private final int voteCount;

    private MovieResult(Builder builder) {
        backdropPath = builder.backdropPath;
        originalTitle = builder.originalTitle;
        id = builder.id;
        popularity = builder.popularity;
        posterPath = builder.posterPath;
        releaseDate = builder.releaseDate;
        title = builder.title;
        voteAverage = builder.voteAverage;
        voteCount = builder.voteCount;
    }


    public static class Builder {
        private String backdropPath;
        private String originalTitle;
        private int id;
        private double popularity;
        private String posterPath;
        private String releaseDate;
        private String title;
        private double voteAverage;
        private  int voteCount;

        public Builder(int id, String title) {
            this.id = id;
            this.title = title;
        }

        public Builder setBackdropPath(String backdropPath) {
            this.backdropPath = "http://image.tmdb.org/t/p/w92" + backdropPath;
            return this;
        }

        public Builder setOriginalTitle(String originalTitle) {
            this.originalTitle = originalTitle;
            return this;
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setPopularity(double popularity) {
            this.popularity = popularity;
            return this;
        }

        public Builder setPosterPath(String posterPath) {
            this.posterPath = "http://image.tmdb.org/t/p/w154" + posterPath;
            return this;
        }

        public Builder setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setVoteAverage(double voteAverage) {
            this.voteAverage = voteAverage;
            return this;
        }
        public Builder setVoteCount(int voteCount) {
            this.voteCount = voteCount;
            return this;
        }

        public MovieResult build() {
            return new MovieResult(this);
        }

    }

    public static Builder newBuilder(int id, String title) {
        return new Builder(id, title);
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public int getId() {
        return id;
    }

    public double getPopularity() {
        return popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getTitle() { return title; }

    public double getVoteAverage() { return voteAverage; }

    public int getVoteCount() { return voteCount; }

    @Override
    public String toString() { return getTitle(); }

}
