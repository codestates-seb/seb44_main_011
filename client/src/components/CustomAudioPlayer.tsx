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
import { ReactComponent as Repeat1 } from "../assets/icons/repeat1.svg";

const StyledAudioPlayer = styled(AudioPlayer).attrs((props) => ({
  customIcons: {
    play: <Play />,
    pause: <Pause />,
    volume: <Volume />,
    volumeMute: <Mute />,
    loop: <Repeat1 />,
    loopOff: <RepeatOff />,
    next: <Next />,
    forward: <Next />,
    rewind: <Previous />,
    previous: <Previous />,
    playAll: <Reapeat />,
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
    fill: "#F0F3F3";
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
  handleSongEnded?: () => void;
};

const CustomAudioPlayer: React.FC<CustomAudioPlayerProps> = ({
  src,
  handleSongEnded,
}) => {
  return (
    <StyledAudioPlayer
      src={src}
      onEnded={handleSongEnded}
      autoPlay={true}
      customIcons={{
        play: <Play />,
        pause: <Pause />,
        volume: <Volume />,
        volumeMute: <Mute />,
        loop: <Repeat1 />,
        loopOff: <RepeatOff />,
        next: <Next />,
        forward: <Next />,
        rewind: <Previous />,
        previous: <Previous />,
      }}
    />
  );
};

export default CustomAudioPlayer;
