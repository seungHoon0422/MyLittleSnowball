/*
Auto-generated by: https://github.com/pmndrs/gltfjsx
*/

import React, { useRef } from 'react'
import { useGLTF } from '@react-three/drei'

function Objet1_10(props) {
  const { nodes, materials } = useGLTF('/Objet1/Objet1_10.glb')
  return (
    <group {...props} dispose={null}>
      <mesh geometry={nodes.Mountain_of_gifts.geometry} material={materials.Mat} />
    </group>
  )
}

useGLTF.preload('/Objet1/Objet1_10.glb')
export default Objet1_10