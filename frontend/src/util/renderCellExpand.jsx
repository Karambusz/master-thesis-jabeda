import {memo, useRef, useState, useCallback, useEffect} from "react";
import { Paper, Popper } from "@material-ui/core";
import { createStyles, makeStyles} from "@material-ui/core/styles";


const useStyles = makeStyles((theme) =>
  createStyles({
    root: {
      alignItems: "center",
      lineHeight: "24px",
      width: "100%",
      height: "100%",
      position: "relative",
      display: "flex",
      "& .MuiRating-root": {
        marginRight: theme.spacing(1)
      },
      "& .cellValue": {
        whiteSpace: "nowrap",
        overflow: "hidden",
        textOverflow: "ellipsis"
      }
    }
  })
);
export const CellExpand = memo(function CellExpand(props) {
  const { width, value } = props;
  const wrapper = useRef(null);
  const cellDiv = useRef(null);
  const cellValue = useRef(null);
  const [anchorEl, setAnchorEl] = useState(null);

  const classes = useStyles();
  const [showFullCell, setShowFullCell] = useState(false);
  const [showPopper, setShowPopper] = useState(false);

  const showCell = useCallback(() => {
    setShowFullCell(true);
  }, []);
  const hideCell = useCallback(() => {
    setShowFullCell(false);
  }, []);

  useEffect(() => {
    if (cellDiv.current) {
      setAnchorEl(cellDiv.current);
    }
  }, []);
  useEffect(() => {
    if (cellValue && cellValue.current) {
      // const isCurrentlyOverflown = isOverflown(cellValue.current);
      setShowPopper(true);
    }
  }, [width]);

  return (
    <div
      ref={wrapper}
      className={classes.root}
      onMouseEnter={showCell}
      onMouseLeave={hideCell}
    >
      <div
        ref={cellDiv}
        style={{
          height: 1,
          width,
          display: "block",
          position: "absolute",
          top: 0
        }}
      />
      <div ref={cellValue} className="cellValue">
        {value}
      </div>
      {showPopper && (
        <Popper
          id={"123"}
          open={showFullCell && anchorEl != null}
          anchorEl={anchorEl}
          style={{ width, marginLeft: -17 }}
        >
          <Paper
            elevation={1}
            style={{ minHeight: wrapper.current.offsetHeight - 2 }}
          >
            <div style={{ padding: 5 }}>{value}</div>
          </Paper>
        </Popper>
      )}
    </div>
  );
});
export function RenderCellExpand(params) {
  return (
    <CellExpand
      value={params.value ? params.value.toString() : ""}
      width={params.colDef ? params.colDef.width :params.colDef.defaultWidth}
    />
  );
}
