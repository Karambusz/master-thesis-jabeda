import './problem-table.scss';
import ReactDataGrid from "@inovua/reactdatagrid-community";
import {CellExpand} from "../../../util/renderCellExpand";
import '@inovua/reactdatagrid-community/index.css'
import NumberFilter from '@inovua/reactdatagrid-community/NumberFilter'
import SelectFilter from '@inovua/reactdatagrid-community/SelectFilter'
import DateFilter from '@inovua/reactdatagrid-community/DateFilter'
import moment from 'moment';
import {IconButton} from "@mui/material";
import {EDIT_ICON} from "@inovua/reactdatagrid-community/packages/ToolBar/examples/icons";
import {faEye} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";


window.moment = moment

const gridStyle = { minHeight: 600 };


const ProblemTable = ({problems, categories, problemTypes, onClick, isHistory}) => {

    const columns = [
        {
            name: 'date',
            header: 'Data',
            defaultWidth: 140,
            filterEditor: DateFilter,
            filterEditorProps: (props, { index }) => {
                return {
                    dateFormat: 'MM-DD-YYYY hh:mm:ss',
                    cancelButton: false,
                    highlightWeekends: false,
                }
            },
        },
        {
            name: 'address',
            header: 'Lokacja',
            sortable: false,
            defaultWidth: 250,
            render: ({data, cellProps}) => {
                return (
                    <CellExpand
                        value={data ? data.address : ''}
                        width={cellProps.defaultWidth}
                    />
                );
            }
        },
        {
            name: 'distance',
            header: 'Odległość (km)',
            defaultWidth: 130,
            filterEditor: NumberFilter,
        },
        {
            name: 'category',
            header: 'Kategoria',
            defaultWidth: 187,
            filterEditor: SelectFilter,
            filterEditorProps: {
                multiple: true,
                wrapMultiple: false,
                placeholder: 'All',
                dataSource: categories.map(c => { return { id: c, label: c}})
            },
            render: ({data, cellProps}) => {
                return (
                    <CellExpand
                        value={data ? data.category : ''}
                        width={cellProps.defaultWidth}
                    />
                );
            }
        },
        {
            name: 'problem',
            header: 'Problem',
            defaultWidth: 251,
            filterEditor: SelectFilter,
            filterEditorProps: {
                multiple: true,
                wrapMultiple: false,
                placeholder: 'All',
                dataSource: problemTypes.map(p => { return { id: p, label: p}})
            },
            render: ({data, cellProps}) => {
                return (
                    <CellExpand
                        value={data ? data.problem : ''}
                        width={cellProps.defaultWidth}
                    />
                );
            }
        },
        {
            name: 'description',
            header: 'Opis',
            sortable: false,
            render: ({data, cellProps}) => {
                 return (
                    <CellExpand
                        value={data ? data.description : ''}
                        width={cellProps.defaultWidth}
                    />
                );
            },
            defaultWidth: 200,
        },
        {
            name: 'problemStatus',
            header: 'Status',
            sortable: false,
            defaultWidth: 100,
        },
       {
            name: "actions",
            header: "",
            defaultWidth: 80,
            render: ({ data }) => (
                !isHistory ? <IconButton aria-label="edit" className='btn' onClick={() => onClick(data)}>
                    {EDIT_ICON}
                </IconButton>
                : <IconButton aria-label="see" className='btn' onClick={() => onClick(data)}>
                        <FontAwesomeIcon  className='btn-icon' icon={faEye}/>
                    </IconButton>
            ),
        },
    ];



    const filterValue = [
        { name: 'date', operator: 'before', type: 'date', value: ''},
        { name: 'address', operator: 'contains', type: 'string', value: '' },
        { name: 'distance', operator: 'gte', type: 'number', value: '' },
        { name: 'category', operator: 'inlist', type: 'select', value: '' },
        { name: 'problem', operator: 'inlist', type: 'select', value: '' },
        { name: 'description', operator: 'contains', type: 'string', value: '' }
    ];


    return (

        <div className='problem-table'>
            <ReactDataGrid
                idProperty="id"
                style={gridStyle}
                enableFiltering={true}
                defaultFilterValue={filterValue}
                columns={columns}
                dataSource={problems}
            />
        </div>
    );
}

export default ProblemTable;