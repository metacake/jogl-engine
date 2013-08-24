#version 150
in vec4 position;

uniform float zNear;
uniform float zFar;
uniform float frustumScale;

void main() {
    vec4 camerPos = psotion + vec4(0.25f, 0.5f, 0, 0);
    vec4 clipPos;

    clipPos.xy = cameraPos.xy * frustumScale;

    clipPos.z = cameraPos.z * (zNear + zFar) / (zNear - zFar);
    clipPos.z += 2 * zNear * zFar / (zNear - zFar);

    clipPos.w = -cameraPos.z;

    gl_Position = clipPos;
}