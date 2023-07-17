import AudioPlayer from "react-h5-audio-player";
import "react-h5-audio-player/lib/styles.css";
import { styled } from "styled-components";
import { ReactComponent as Play } from "../assets/icons/play.svg";
import { ReactComponent as Pause } from "../assets/icons/pause.svg";
import { ReactComponent as Volume } from "../assets/icons/volume.svg";
import { ReactComponent as Reapeat } from "../assets/icons/repeat.svg";
import { ReactComponent as Next } from "../assets/icons/next.svg";
import { ReactComponent as Previous } from "../assets/icons/previous.svg";
import { ReactComponent as Mute } from "../assets/icons/mute.svg";
import { ReactComponent as RepeatOff } from "../assets/icons/repeatOff.svg";

const StyledAudioPlayer = styled(AudioPlayer).attrs((props) => ({
  customIcons: {
    play: <Play />,
    pause: <Pause />,
    volume: <Volume fill="#F0F3F3" />,
    volumeMute: <Mute />,
    loop: <Reapeat fill="#F0F3F3" />,
    loopOff: <RepeatOff fill="#F0F3F3" />,
    next: <Next fill="#F0F3F3" />,
    forward: <Next fill="#F0F3F3" />,
    rewind: <Previous fill="#F0F3F3" />,
    previous: <Previous fill="#F0F3F3" />,
    ...props.customIcons,
  },
}))`
  width: 100%;
  background-color: transparent;
  box-shadow: none;
  font-family: var(--font-quicksand);
  font-size: 12px;
  padding: 0px;

  > svg {
    width: 16px;
    height: 16px;
  }

  .rhap_time {
    color: var(--white);
    font-size: 14px;
  }

  .rhap_progress-filled {
    background-color: var(--primary);
  }

  .rhap_progress-indicator {
    background-color: var(--primary);
    width: 12px;
    height: 12px;
    top: -3px;
    border: 1px solid var(--white);
    box-shadow: 0px 0px 5px 1px rgba(255, 255, 255, 0.4);
  }

  .rhap_volume-indicator {
    background-color: var(--gray-200);
    width: 12px;
    height: 12px;
  }

  .rhap_controls-section {
    height: 24px;
    margin-right: 6px;
  }

  .rhap_controls-section {
    display: flex;
    align-items: center;
  }

  .rhap_main-controls-button {
    height: auto;
  }
`;

type CustomAudioPlayerProps = {
  src: string;
};

const CustomAudioPlayer: React.FC<CustomAudioPlayerProps> = ({ src }) => {
  return (
    <StyledAudioPlayer
      src={src}
      autoPlay={true}
      customIcons={{
        play: <Play />,
        pause: <Pause />,
        volume: <Volume fill="#F0F3F3" />,
        volumeMute: <Mute />,
        loop: <Reapeat fill="#F0F3F3" />,
        loopOff: <RepeatOff fill="#F0F3F3" />,
        next: <Next fill="#F0F3F3" />,
        forward: <Next fill="#F0F3F3" />,
        rewind: <Previous fill="#F0F3F3" />,
        previous: <Previous fill="#F0F3F3" />,
      }}
    />
  );
};

export default CustomAudioPlayer;
