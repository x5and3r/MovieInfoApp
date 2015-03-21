package com.example.sanya.ua.movieinformerjb;

public class MovieResult {
    private String backdropPath;
    private String originalTitle;
    private int id;
    private double popularity;
    private String posterPath;
    private String releaseDate;
    private String title;
    private double voteAverage;
    private int voteCount;

        public void setBackdropPath(String backdropPath) {
            this.backdropPath = "http://image.tmdb.org/t/p/w92" + backdropPath;
        }

        public void setOriginalTitle(String originalTitle) {
            this.originalTitle = originalTitle;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setPopularity(double popularity) {
            this.popularity = popularity;
        }

        public void setPosterPath(String posterPath) {
            this.posterPath = "http://image.tmdb.org/t/p/w154" + posterPath;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setVoteAverage(double voteAverage) {
            this.voteAverage = voteAverage;
        }
        public void setVoteCount(int voteCount) {
            this.voteCount = voteCount;
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
