import java.util.*;

// Module 10: Rating & Review System
public class RatingReview {
    private String reviewId;
    private String bookingId;
    private String reviewerId;    // who gave the review (user or driver)
    private String revieweeId;    // who received the review
    private int rating;           // 1 to 5 stars
    private String comment;

    // Store all reviews
    private static List<RatingReview> allReviews = new ArrayList<>();

    public RatingReview(String reviewId, String bookingId, String reviewerId, String revieweeId, int rating, String comment) {
        this.reviewId = reviewId;
        this.bookingId = bookingId;
        this.reviewerId = reviewerId;
        this.revieweeId = revieweeId;
        this.rating = Math.min(5, Math.max(1, rating)); // clamp between 1-5
        this.comment = comment;
    }

    public void submitReview() {
        allReviews.add(this);
        System.out.println("[GoPro] Review submitted: " + rating + " stars - \"" + comment + "\"");
    }

    public static double getAverageRating(String personId) {
        double sum = 0;
        int count = 0;
        for (RatingReview r : allReviews) {
            if (r.revieweeId.equals(personId)) {
                sum += r.rating;
                count++;
            }
        }
        double avg = count > 0 ? sum / count : 0;
        System.out.println("[GoPro] Average rating for " + personId + ": " + String.format("%.1f", avg) + " (" + count + " reviews)");
        return avg;
    }

    public static void showReviewsFor(String personId) {
        System.out.println("[GoPro] ===== Reviews for " + personId + " =====");
        for (RatingReview r : allReviews) {
            if (r.revieweeId.equals(personId)) {
                System.out.println("  " + r.rating + " stars - \"" + r.comment + "\" (Ride: " + r.bookingId + ")");
            }
        }
    }

    // Getters
    public int getRating() { return rating; }
    public String getComment() { return comment; }

    @Override
    public String toString() {
        return "Review{" + rating + " stars, comment='" + comment + "'}";
    }

    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║   GoPro Rating & Review Module Demo        ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        // Test 1: Submit reviews
        System.out.println("--- Test 1: Submit Reviews ---");
        RatingReview r1 = new RatingReview("REV001", "R001", "U001", "D001", 5, "Excellent driver! Very polite.");
        RatingReview r2 = new RatingReview("REV002", "R001", "D001", "U001", 4, "Good passenger.");
        RatingReview r3 = new RatingReview("REV003", "R002", "U001", "D002", 5, "Very professional service.");

        r1.submitReview();
        r2.submitReview();
        r3.submitReview();

        // Test 2: Get average rating
        System.out.println("\n--- Test 2: Average Rating ---");
        RatingReview.getAverageRating("D001");
        RatingReview.getAverageRating("D002");

        // Test 3: Show reviews
        System.out.println("\n--- Test 3: Show Reviews for Driver D001 ---");
        RatingReview.showReviewsFor("D001");

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║   Rating & Review Demo Complete!           ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }
}
