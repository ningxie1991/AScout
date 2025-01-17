import { Button } from '@material-ui/core'
import React, { useState } from 'react'
import { Arrow, useLayer } from 'react-laag'
import styled from 'styled-components'
import './AttractionMarker.css'

const StyledMarker = styled.div`
  position: absolute;
  width: 38px;
  height: 37px;
  background-image: url(https://icon-library.com/images/pin-icon-png/pin-icon-png-9.jpg);
  background-size: contain;
  background-repeat: no-repeat;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  -webkit-transform: translate(-50%, -50%);
  -ms-transform: translate(-50%, -50%);
  transform: translate(-50%, -50%);
  cursor: pointer;
  &:hover {
    z-index: 1;
  }
`
const InfoBox = styled.div`
  padding: 0;
  box-shadow: 1px 1px 10px 1px rgba(0, 0, 0, 0.2);
  background-color: #fff;
  &:hover {
    z-index: 1;
  }
`

const InfoDetail = styled.span`
  padding: 0.8em;
  font-size: 10pt;
  display: block;
  width: 200px;
  word-wrap: break-word;
`

export default function AttractionMarker({ key, attraction, onAddAttraction }) {
  const [isShown, setShown] = useState(true)
  const [isOpen, setOpen] = useState(true)

  // specify the appended div id on the container option
  const { triggerProps, layerProps, arrowProps, renderLayer } = useLayer({
    isOpen,
    placement: 'bottom-center',
    triggerOffset: 0,
    onOutsideClick: () => setOpen(false),
    onDisappear: (type) => {
      //if (type === 'full') setOpen(false)
    },
  })

  return (
    <div key={key}>
      {isShown && (
        <StyledMarker
          key={key}
          onClick={() => setOpen((prev) => !prev)}
          {...triggerProps}
        />
      )}
      {isOpen &&
        renderLayer(
          <InfoBox key={key} {...layerProps}>
            <img
              src={attraction.pictureUrl}
              width='200'
              height='150'
              alt='attraction marker'
            />
            <br />
            <InfoDetail>{attraction.name}</InfoDetail>
            <Button
              color='primary'
              style={{
                textTransform: 'none',
                fontWeight: 'normal',
                fontSize: '10pt',
                float: 'right',
              }}
              onClick={() => {
                onAddAttraction(attraction)
                setOpen(false)
                setShown(false)
              }}
            >
              Add to My Attractions
            </Button>
            <Arrow {...arrowProps} />
          </InfoBox>
        )}
    </div>
  )
}
