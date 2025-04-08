import React from 'react';

interface LampIconProps extends React.SVGProps<SVGSVGElement> {
  size?: number | string;
}

const LampIcon: React.FC<LampIconProps> = ({ size = 24, ...props }) => {
  return (
    <svg
      width={size}
      height={size}
      viewBox="0 0 64 64"
      fill="currentColor"
      xmlns="http://www.w3.org/2000/svg"
      {...props}
    >
      <path
        fillRule="evenodd"
        clipRule="evenodd"
        d="M28,2L28,6C28,7.104 28.896,8 30,8C31.104,8 32,7.104 32,6L32,2C32,0.896 31.104,-0 30,0C28.896,0 28,0.896 28,2Z"
        transform="matrix(6.12323e-17,1,-1,6.12323e-17,61,0)"
      />
      <path
        fillRule="evenodd"
        clipRule="evenodd"
        d="M28,2L28,6C28,7.104 28.896,8 30,8C31.104,8 32,7.104 32,6L32,2C32,0.896 31.104,0 30,0C28.896,-0 28,0.896 28,2Z"
        transform="matrix(0.707107,0.707107,-0.707107,0.707107,31.2929,-11.7193)"
      />
      <path
        fillRule="evenodd"
        clipRule="evenodd"
        d="M28,2L28,6C28,7.104 28.896,8 30,8C31.104,8 32,7.104 32,6L32,2C32,0.896 31.104,0 30,0C28.896,-0 28,0.896 28,2Z"
        transform="matrix(6.12323e-17,-1,1,6.12323e-17,3,60)"
      />
      <path
        fillRule="evenodd"
        clipRule="evenodd"
        d="M28,2L28,6C28,7.104 28.896,8 30,8C31.104,8 32,7.104 32,6L32,2C32,0.896 31.104,0 30,0C28.896,-0 28,0.896 28,2Z"
        transform="matrix(-1,-3.33067e-16,3.33067e-16,-1,62,10)"
      />
      <path
        fillRule="evenodd"
        clipRule="evenodd"
        d="M28,2L28,6C28,7.104 28.896,8 30,8C31.104,8 32,7.104 32,6L32,2C32,0.896 31.104,0 30,0C28.896,-0 28,0.896 28,2Z"
        transform="matrix(0.707107,-0.707107,0.707107,0.707107,-9.7193,30.7071)"
      />
      <path
        fillRule="evenodd"
        clipRule="evenodd"
        d="M24,50.998L24,61C24,62.105 24.895,63 26,63L38,63C39.105,63 40,62.105 40,61L40,51.471C40.037,51.32 40.056,51.162 40.056,50.999C40.056,48.278 41.267,45.699 43.359,43.96C47.411,40.659 50,35.629 50,30C50,20.066 41.934,12 32,12C22.066,12 14,20.066 14,30C14,35.636 16.595,40.67 20.69,43.929C22.785,45.674 24,48.265 24,50.998L24,50.998ZM36,53L28,53L28,59C28,59 36,59 36,59L36,53ZM34,49L36.21,49C36.694,45.844 38.317,42.946 40.809,40.878C40.814,40.874 40.819,40.87 40.824,40.866C43.981,38.298 46,34.383 46,30C46,22.273 39.727,16 32,16C24.273,16 18,22.273 18,30C18,34.374 20.012,38.283 23.193,40.809C23.205,40.818 23.216,40.827 23.227,40.836C25.732,42.915 27.362,45.828 27.847,49L30,49L30,30L26,30C24.896,30 24,29.104 24,28C24,26.896 24.896,26 26,26L38,26C39.104,26 40,26.896 40,28C40,29.104 39.104,30 38,30L34,30L34,49Z"
      />
    </svg>
  );
};

export default LampIcon;