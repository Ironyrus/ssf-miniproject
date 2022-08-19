package trafficweatherapp.project.Services;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    String MatchNumber;
    String RoundNumber;


    // JsonProperty annotation is needed because Spring cannot recognize attributes starting with capital letters
    @JsonProperty("HomeTeam")
    String HomeTeam;
    
    @JsonProperty("AwayTeam")
    String AwayTeam;

    @JsonProperty("HomeTeamScore")
    String HomeTeamScore;
    
    String AwayTeamScore;
    
    public User() {
    }

    public User(String matchNumber, String roundNumber, String homeTeam, String awayTeam, String homeTeamScore,
            String awayTeamScore) {
        MatchNumber = matchNumber;
        RoundNumber = roundNumber;
        HomeTeam = homeTeam;
        AwayTeam = awayTeam;
        HomeTeamScore = homeTeamScore;
        AwayTeamScore = awayTeamScore;
    }
    
   

    public String getMatchNumber() {
        return MatchNumber;
    }

    public void setMatchNumber(String matchNumber) {
        MatchNumber = matchNumber;
    }

    public String getRoundNumber() {
        return RoundNumber;
    }

    public void setRoundNumber(String roundNumber) {
        RoundNumber = roundNumber;
    }

    public String getHomeTeam() {
        return HomeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        HomeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return AwayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        AwayTeam = awayTeam;
    }

    public String getHomeTeamScore() {
        return HomeTeamScore;
    }

    public void setHomeTeamScore(String homeTeamScore) {
        HomeTeamScore = homeTeamScore;
    }

    public String getAwayTeamScore() {
        return AwayTeamScore;
    }

    public void setAwayTeamScore(String awayTeamScore) {
        AwayTeamScore = awayTeamScore;
    }

    @Override
    public String toString() {
        return "User [AwayTeam=" + AwayTeam + ", AwayTeamScore=" + AwayTeamScore + ", HomeTeam=" + HomeTeam
                + ", HomeTeamScore=" + HomeTeamScore + ", MatchNumber=" + MatchNumber + ", RoundNumber=" + RoundNumber
                + "]";
    }

}
