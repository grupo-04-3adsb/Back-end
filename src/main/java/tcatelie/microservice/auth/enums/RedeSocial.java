package tcatelie.microservice.auth.enums;

public enum RedeSocial {

  INSTAGRAM("Instagram", 1),
  FACEBOOK("Facebook", 2),
  LINKEDIN("Linkedin", 3),
  PINTEREST("Pinterest", 4),
  TWITTER("Twitter", 5),
  YOUTUBE("Youtube", 6),
  TIKTOK("TikTok", 7),
  SNAPCHAT("Snapchat", 8),
  WHATSAPP("Whatsapp", 9),
  TELEGRAM("Telegram", 10),
  SKYPE("Skype", 11),
  DISCORD("Discord", 12),
  SLACK("Slack", 13),
  MEDIUM("Medium", 14),
  GITHUB("Github", 15),
  BEHANCE("Behance", 16),
  DRIBBBLE("Dribbble", 17);

  private String nome;
  private Integer code;

  RedeSocial(String instagram, int i) {
  }
}
