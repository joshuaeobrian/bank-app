import {Spinner} from 'react-bootstrap'

export const Loader = ({loading}) => {
    if(!loading){
        return null;
    }
    const style = {
        width: 'fit-content',
        margin: '50px auto'
    }
    return (
        <div style={style}>
            <Spinner animation="border" role="status">
                <span className="visually-hidden">Loading...</span>
            </Spinner>
        </div>
    )
}
