public class Topic {
    private int topicID;
    private String topic;
    public Topic (int topicID, String topic){
        this.topic=topic;
        this.topicID=topicID;

    }

    public int getTopicID() {
        return topicID;
    }

    public void setTopicID(int topicID) {
        this.topicID = topicID;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
