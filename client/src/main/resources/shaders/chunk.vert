#version 120

attribute vec3 blockPosition;
attribute vec2 blockTexture;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;

varying vec2 varTexture;

void main() {
    gl_Position = projectionMatrix * viewMatrix * vec4(blockPosition, 1);
    varTexture = blockTexture;
}